package com.example.pricecompareredis.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductGrp {

    private String prodGrpId;               // HOME001, ...
    private List<Product> productList;      // {HOME001, [{"000A", 25000"}, {} ...]

}
