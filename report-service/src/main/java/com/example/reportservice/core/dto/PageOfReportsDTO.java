package com.example.reportservice.core.dto;

import com.example.reportservice.core.entity.ReportEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageOfReportsDTO {
    private int number;
    private int size;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_elements")
    private long totalElements;
    private boolean first;
    private boolean last;
    @JsonProperty("numberOfElements")
    private int numberOfElements;
    private List<ReportEntity> content;

    public PageOfReportsDTO(int number, int size) {
        this.number = number;
        this.size = size;
    }
}
