package com.example.flatservice.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FlatsFilter {
    private Integer priceFrom;
    private Integer priceTo;
    private Integer bedroomsFrom;
    private Integer bedroomsTo;
    private Integer areaFrom;
    private Integer areaTo;
    private Integer[] floors;
}
