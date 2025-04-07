package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.model.ProductEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUploadService {
    @Autowired
    private ProductService productService;

    private static final int BATCH_SIZE = 1000; // Taille du batch pour éviter d'épuiser la mémoire

    public boolean parseNudgerCSV(MultipartFile file) {
        List<ProductEntity> batch = new ArrayList<>();
        try {
            Path tempFile = Files.createTempFile("upload-", ".csv");
            file.transferTo(tempFile.toFile());

            try (BufferedReader reader = Files.newBufferedReader(tempFile, StandardCharsets.UTF_8)) {
                CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                        .setHeader() // Définit la première ligne comme en-tête
                        .setSkipHeaderRecord(true) // Ignore l’en-tête lors du parsing
                        .build();

                CSVParser csvParser = new CSVParser(reader, csvFormat);

                for (CSVRecord record : csvParser) {
                    if (record.get("gtinType").compareTo("GTIN_13") == 0) {
                        ProductEntity product = new ProductEntity();
                        product.setEanCode(record.get("gtin"));
                        product.setBrand(record.get("brand"));
                        product.setName(record.get("name"));
                        try {
                            long timestamp = Long.parseLong(record.get("last_updated"));
                            LocalDateTime lastUpdated = Instant.ofEpochMilli(timestamp)
                                    .atZone(ZoneId.systemDefault()) // Utilise le fuseau horaire du système
                                    .toLocalDateTime();
                            product.setLastUpdated(lastUpdated);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Erreur de conversion du timestamp : " + record.get("last_updated"));
                        }
                        product.setNudgerUrl(record.get("url"));

                        batch.add(product);

                        // Sauvegarde quand la taille du batch atteint la limite
                        if (batch.size() >= BATCH_SIZE) {
                            productService.saveAll(batch);
                            batch.clear(); // Vide la liste pour le prochain batch
                        }
                    }
                }

                // Sauvegarde les derniers enregistrements restants
                if (!batch.isEmpty()) {
                    productService.saveAll(batch);
                }

            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            }
            // 3️⃣ Supprimer le fichier temporaire après le traitement
            Files.deleteIfExists(tempFile);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du traitement du fichier CSV : " + e.getMessage());
        }
        return true;
    }
}
