package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.ProductEntityDto;
import be.dsystem.thepriceisright.model.ProductEntity;
import be.dsystem.thepriceisright.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/all")
    public List<ProductEntity> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping(value="/allbyname")
    public Page<ProductEntity> getAllProductsByName(@RequestParam String name, @RequestParam int page, @RequestParam int size) {
        return productService.findByName(name, page, size);
    }
    @GetMapping(value="/allbypage")
    public Page<ProductEntityDto> getAllProductsByPage(@RequestParam int page, @RequestParam int size) {
        return productService.finAll(page, size);
    }
}
