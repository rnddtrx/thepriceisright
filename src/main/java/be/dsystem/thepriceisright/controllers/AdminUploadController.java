package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.ProductEntityDto;
import be.dsystem.thepriceisright.model.ProductEntity;
import be.dsystem.thepriceisright.services.AdminUploadService;
import be.dsystem.thepriceisright.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/adminupload")
public class AdminUploadController {
    @Autowired
    private AdminUploadService adminUploadService;
    @Autowired
    private ProductService productService;

    @PostMapping(value ="/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        adminUploadService.parseNudgerCSV(file);
        return ResponseEntity.ok("Fichier CSV traité et enregistré avec succès !");
    }



}
