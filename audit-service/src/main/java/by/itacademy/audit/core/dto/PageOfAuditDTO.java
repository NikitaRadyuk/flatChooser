package by.itacademy.audit.core.dto;

import by.itacademy.audit.core.entity.AuditEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PageOfAuditDTO {
    private int number;
    private int size;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private long totalElements;

    private boolean first;

    @JsonProperty("number_of_elements")
    private int numberOfElements;

    private boolean last;

    private List<AuditEntity> content;

    public PageOfAuditDTO(int number, int size) {
        this.number = number;
        this.size = size;
    }

}
