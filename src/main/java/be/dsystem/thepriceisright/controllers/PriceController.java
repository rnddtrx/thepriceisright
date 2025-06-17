package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.PriceEntityDto;
import be.dsystem.thepriceisright.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    @Autowired
    private PriceService priceService;

    // get by id
    @GetMapping("/{id}")
    public PriceEntityDto getPriceById(@PathVariable int id) {
        return priceService.getPriceById(id);
    }

    // get all by page
    @GetMapping("/allbypage")
    public Page<PriceEntityDto> getAllPrices(@RequestParam int page, @RequestParam int size) {
        return priceService.getPagedPrices(page,size);
    }

    // add price
    @PostMapping("/add")
    public void addPrice(@RequestBody PriceEntityDto priceEntityDto) {
        priceService.addPrice(priceEntityDto);
    }

    // update price by id
    @PutMapping("/{id}")
    public PriceEntityDto updatePriceById(@PathVariable int id, @RequestBody PriceEntityDto priceEntityDto) {
        return priceService.updatePriceById(id, priceEntityDto);
    }

    // delete price by id
    @DeleteMapping("/{id}")
    public void deletePriceById(@PathVariable int id) {
        priceService.deletePriceById(id);
    }



}
