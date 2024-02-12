package com.example.flatservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageOfFlatDTO {
    @JsonProperty("number")
    private Integer number;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_elements")
    private Long totalElements;

    @JsonProperty("first")
    private Boolean first;

    @JsonProperty("number_of_elements")
    private Integer numberOfElements;

    @JsonProperty("last")
    private Boolean last;

    @JsonProperty("content")
    private List<FlatDTO> content;
}
