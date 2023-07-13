package com.example.pricecompareredis.service;

import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LowestPriceServiceImpl implements LowestPriceService{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Set getZsetValue(String key) {

        Set tempSet = new HashSet();
        tempSet = redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);

        return tempSet;
    }

    @Override
    public int setNewProduct(Product product) {

        int rank = 0;

        redisTemplate.opsForZSet().add(product.getProdGrpId(), product.getProductID(), product.getPrice());
        rank = redisTemplate.opsForZSet().rank(product.getProdGrpId(), product.getProductID()).intValue();

        return rank;
    }

    @Override
    public int setNewProductGrp(ProductGrp productGrp) {

        List<Product> product = productGrp.getProductList();
        String productId = product.get(0).getProductID();
        int price = product.get(0).getPrice();

        redisTemplate.opsForZSet().add(productGrp.getProdGrpId(), productId, price);

        return redisTemplate.opsForZSet().zCard(productGrp.getProdGrpId()).intValue();
    }

    @Override
    public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {

        redisTemplate.opsForZSet().add(keyword, prodGrpId, score);

        int rank = redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();

        return rank;
    }

}
