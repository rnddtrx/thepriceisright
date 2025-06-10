package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.ProductEntityDto;
import be.dsystem.thepriceisright.dtos.ShopEntityDto;
import be.dsystem.thepriceisright.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/shops")
public class ShopContoller {
    @Autowired
    private ShopService shopService;

    @GetMapping(value="/allbypage")
    public Page<ShopEntityDto> getAllProductsByPage(@RequestParam int page, @RequestParam int size) {
        return shopService.getShops(page, size);
    }

    @GetMapping
    public ShopEntityDto getProductById(@RequestParam int id) {
        return shopService.getShopById(id);
    }

    @PostMapping
    public ShopEntityDto createShop(ShopEntityDto shopEntityDto) {
        return shopService.createShop(shopEntityDto);
    }
}
