package com.mohsenko.e_commerce_mohsenko.controller;

import com.mohsenko.e_commerce_mohsenko.dto.brand.BrandUserDetailsDto;
import com.mohsenko.e_commerce_mohsenko.dto.brand.BrandUserSummaryDto;
import com.mohsenko.e_commerce_mohsenko.dto.pagination.PageResponse;
import com.mohsenko.e_commerce_mohsenko.service.BrandService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/brands")
@RequiredArgsConstructor
@Validated
public class BrandPublicController {
    private final BrandService brandService;

    @GetMapping("/{slug}")
    public ResponseEntity<BrandUserDetailsDto> getBrand(@PathVariable String slug) {
        return ResponseEntity.ok(brandService.getActiveBrand(slug));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BrandUserSummaryDto>> getAllBrands(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
            @RequestParam(defaultValue = "asc") String direction)
    {
        // setup pagination settings
        Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, Math.min(size, 50), Sort.by(dir, "name"));

        return ResponseEntity.ok(brandService.getAllActiveBrands(search, pageable));
    }

}
