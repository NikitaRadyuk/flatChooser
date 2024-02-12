package com.example.flatservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class FlatDTO {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;

    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    @JsonProperty("offer_type")
    private String offerType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("bedrooms")
    private String bedrooms;

    @JsonProperty("area")
    private Integer area;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("floor")
    private Integer floor;
}
