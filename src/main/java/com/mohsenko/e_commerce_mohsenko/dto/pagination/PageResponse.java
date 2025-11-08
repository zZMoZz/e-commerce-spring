package com.mohsenko.e_commerce_mohsenko.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageResponse<T> extends SliceResponse<T> {
    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_elements")
    private Long totalElements;
}
