package com.example.pricecompareredis.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {

    private String keyword;                         // 가전 - 냉장고(HOME0001), 냉장고(HOME0002)
    private List<ProductGrp> productGrpList;      // [{"HOME0001",[{"000A", 25000}, {}... ]} ... }]

}
