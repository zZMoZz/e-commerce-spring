package com.mohsenko.e_commerce_mohsenko.dto.pagination;

import lombok.Data;

@Data
public class PageResponse<T> extends SliceResponse<T> {
    private Integer totalPages;
    private Long totalElements;
}
