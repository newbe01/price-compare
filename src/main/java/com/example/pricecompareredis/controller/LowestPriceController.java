package com.example.pricecompareredis.controller;

import com.example.pricecompareredis.service.LowestPriceServiceImpl;
import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RequestMapping("/")
@RestController
public class LowestPriceController {

    @Autowired
    private LowestPriceServiceImpl lowestPriceService;

    @GetMapping("/getZSETValue")
    public Set GetZsetValue(String key) {
        try {
            return lowestPriceService.getZsetValue(key);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/product")
    public int SetNewProduct(@RequestBody Product product) {
        return lowestPriceService.setNewProduct(product);
    }

    @PutMapping("/productGroup")
    public int SetNewProductGroup(@RequestBody ProductGrp productGrp) {
        return lowestPriceService.setNewProductGrp(productGrp);
    }

    @PutMapping("/productGroupToKeyword")
    int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
        return lowestPriceService.setNewProductGrpToKeyword(keyword, prodGrpId, score);
    }

    @GetMapping("/productPrice/lowest")
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.GetLowestPriceProductByKeyword(keyword);
    }

}
