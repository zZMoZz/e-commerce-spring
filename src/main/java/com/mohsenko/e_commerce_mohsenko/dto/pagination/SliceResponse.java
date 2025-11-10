package com.mohsenko.e_commerce_mohsenko.dto.pagination;

import lombok.Data;

import java.util.List;

@Data
public class SliceResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean hasNext;
}
