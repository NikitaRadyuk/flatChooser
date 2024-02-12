package com.example.flatservice.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FlatParseDTO {
    private String url;
}
