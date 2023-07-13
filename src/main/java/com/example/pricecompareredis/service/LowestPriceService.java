package com.example.pricecompareredis.service;

import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setNewProduct(Product product);

    int setNewProductGrp(ProductGrp productGrp);

    int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score);

}
