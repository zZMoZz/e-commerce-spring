package com.mohsenko.e_commerce_mohsenko.dto.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SliceResponse<T> {
    private List<T> content;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("has_next")
    private Boolean hasNext;
}
