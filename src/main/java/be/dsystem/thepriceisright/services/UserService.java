package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.UserEntityDto;
import be.dsystem.thepriceisright.dtos.UserEntityProfileDto;
import be.dsystem.thepriceisright.mappers.UserEntityMapper;
import be.dsystem.thepriceisright.mappers.UserEntityProfileMapper;
import be.dsystem.thepriceisright.model.UserEntity;
import be.dsystem.thepriceisright.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private UserEntityProfileMapper userEntityProfileMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Add user without role
    public UserEntityDto addUser(UserEntityDto userDto) {


        //Convert dto to entity
        var userEntity = userEntityMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        //Save entity
        var savedUser = userRepository.save(userEntity);
        //Convert entity to dto
        return userEntityMapper.toDto(savedUser);
    }

    //Get user by username
    public Optional<UserEntityProfileDto> getUserByUsername(String userName) {
        Optional<UserEntity> ue = userRepository.findByUserName(userName);
        // la méthode map de optional (pas confondre avec le mapping du dto)
        // retourne un Optional<UserEntityDto> ou un optional vide si l'utilisateur n'existe pas
        return ue.map(userEntity -> userEntityProfileMapper.toDto(userEntity));
    }

    //Get user by username
    public UserEntity getUserEntityByUsername(String userName) {
        var userEntity = userRepository.findByUserName(userName);
        return userEntity.orElse(null);
    }

    public void addRoleToUser(String userName, String roleName) {
        var userEntity = userRepository.findByUserName(userName);
        if (userEntity.isPresent()) {
            var roleEntity = roleService.getRoleByName(roleName);
            if (roleEntity == null) {
                return;
            }
            userEntity.get().getRoleEntities().add(roleEntity);
            var savedUser = userRepository.save(userEntity.get());
            userEntityMapper.toDto(savedUser);
        }
    }

    public Optional<UserEntityProfileDto> getMyUser() {
        // get the current user from the security context
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // use the getUserByUsername method to retrieve the user
        return getUserByUsername(currentUsername);
    }

    public Optional<byte[]> getProfilePicture() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> user = userRepository.findByUserName(currentUsername);

        Path imagePath;

        if (user.isPresent()) {
            imagePath = Paths.get("/opt/thepriceisright/picture/" + user.get().getProfilePictureUrl());
        } else {
            throw new RuntimeException("User not found");
        }

        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file: " + e.getMessage());
        }

        return Optional.of(imageBytes);
    }

    public Optional<UserEntity> uploadProfilePicture(MultipartFile file) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> user = userRepository.findByUserName(currentUsername);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            String fileName = userEntity.getUserName() + ".jpg";
            Path imagePath = Paths.get("/opt/thepriceisright/picture/" + fileName);

            try {
                Files.createDirectories(imagePath.getParent());
                try (InputStream inputStream = file.getInputStream()) {
                    BufferedImage originalImage = ImageIO.read(inputStream);
                    if (originalImage == null) throw new IOException("Image non valide");

                    // 1. Déterminer le ratio et crop centré
                    int width = originalImage.getWidth();
                    int height = originalImage.getHeight();
                    int cropSize = Math.min(width, height);
                    int cropX = (width - cropSize) / 2;
                    int cropY = (height - cropSize) / 2;

                    BufferedImage cropped = originalImage.getSubimage(cropX, cropY, cropSize, cropSize);

                    // 2. Redimensionner à 100x100
                    BufferedImage resized = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = resized.createGraphics();

                    // Fond blanc pour compenser la transparence PNG
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, 100, 100);

                    // Image redimensionnée
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(cropped, 0, 0, 100, 100, null);
                    g2d.dispose();

                    // 3. Toujours sauvegarder en JPG
                    Path targetPath = imagePath.getParent().resolve(fileName);
                    ImageIO.write(resized, "jpg", targetPath.toFile());

                    // 4. Enregistrer le nom dans la base
                    userEntity.setProfilePictureUrl(fileName);
                    return Optional.of(userRepository.save(userEntity));
                }
            } catch (IOException e) {
                throw new RuntimeException("Error saving image file: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
