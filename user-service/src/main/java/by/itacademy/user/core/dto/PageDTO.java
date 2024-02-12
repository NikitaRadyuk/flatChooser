package by.itacademy.user.core.dto;

import by.itacademy.user.core.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private Boolean isFirst;
    private Boolean isLast;
    private Integer numberOfElements;
    private List<UserEntity> content;
}
