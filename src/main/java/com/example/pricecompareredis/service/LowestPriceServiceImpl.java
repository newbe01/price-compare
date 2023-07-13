package com.example.pricecompareredis.service;

import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGrp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Keyword GetLowestPriceProductByKeyword(String keyword) {

        Keyword returnInfo = new Keyword();
        List<ProductGrp> tempProdGrp = getProdGrpUsingKeyword(keyword);

        returnInfo.setKeyword(keyword);
        returnInfo.setProductGrpList(tempProdGrp);

        return returnInfo;
    }

    public List<ProductGrp> getProdGrpUsingKeyword(String keyword) {
        List<ProductGrp> returnInfo = new ArrayList<>();

        List<String> prodGrpIdList = new ArrayList<>();
        prodGrpIdList = List.copyOf(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9));
        List<Product> tempProdList = new ArrayList<>();

        for (final String prodGrpId : prodGrpIdList) {

            ProductGrp tempProdGrp = new ProductGrp();

            Set prodAndPriceList = new HashSet();
            prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(prodGrpId, 0, 9);

            Iterator<Object> prodPriceObj = prodAndPriceList.iterator();

            while (prodPriceObj.hasNext()) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> prodPriceMap = objectMapper.convertValue(prodPriceObj.next(), Map.class);
                Product tempProduct = new Product();

                tempProduct.setProductID(prodPriceMap.get("value").toString());
                tempProduct.setPrice(Double.valueOf(prodPriceMap.get("score").toString()).intValue());
                tempProduct.setProdGrpId(prodGrpId);

                tempProdList.add(tempProduct);
            }

            tempProdGrp.setProdGrpId(prodGrpId);
            tempProdGrp.setProductList(tempProdList);

            returnInfo.add(tempProdGrp);
        }

        return returnInfo;
    }

}
