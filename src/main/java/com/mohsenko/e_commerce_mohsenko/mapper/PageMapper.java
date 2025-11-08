package com.mohsenko.e_commerce_mohsenko.mapper;

import com.mohsenko.e_commerce_mohsenko.dto.pagination.PageResponse;
import com.mohsenko.e_commerce_mohsenko.dto.pagination.SliceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public final class PageMapper {

    private PageMapper() {} // prevent instantiation

    public static <T> SliceResponse<T> toSliceResponse(Slice<T> slice) {
        SliceResponse<T> response = new SliceResponse<>();
        response.setContent(slice.getContent());
        response.setPageNumber(slice.getNumber());
        response.setPageSize(slice.getSize());
        response.setHasNext(slice.hasNext());
        return response;
    }

    public static <T> PageResponse<T> toPageResponse(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();

        // set the base info
        response.setContent(page.getContent());
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setHasNext(page.hasNext());

        // set the additional info
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());

        return response;
    }
}
