package com.example.pricecompareredis.controller;

import com.example.pricecompareredis.service.LowestPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
