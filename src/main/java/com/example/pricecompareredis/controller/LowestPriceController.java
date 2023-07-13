package com.example.pricecompareredis.controller;

import com.example.pricecompareredis.service.LowestPriceServiceImpl;
import com.example.pricecompareredis.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/")
@RestController
public class LowestPriceController {

    @Autowired
    private LowestPriceServiceImpl lowestPriceService;

    @GetMapping("/getZSETValue")
    public Set GetZsetValue(String key) {

        return lowestPriceService.getZsetValue(key);
    }

    @PutMapping("/product")
    public int SetNewProduct(@RequestBody Product product) {
        return lowestPriceService.setNewProduct(product);
    }

}
