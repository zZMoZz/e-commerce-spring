package com.mohsenko.e_commerce_mohsenko.controller;

import com.mohsenko.e_commerce_mohsenko.dto.brand.BrandAdminDetailsDto;
import com.mohsenko.e_commerce_mohsenko.dto.brand.BrandAdminSummaryDto;
import com.mohsenko.e_commerce_mohsenko.dto.brand.BrandCreateDto;
import com.mohsenko.e_commerce_mohsenko.dto.brand.BrandUpdateDto;
import com.mohsenko.e_commerce_mohsenko.dto.pagination.PageResponse;
import com.mohsenko.e_commerce_mohsenko.service.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/admin/brands", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class BrandAdminController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandAdminDetailsDto> createBrand(@RequestBody @Valid BrandCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(brandService.createBrand(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BrandAdminDetailsDto> updateBrand(@PathVariable @Positive int id,
                                                            @RequestBody @Valid BrandUpdateDto dto) {
        return ResponseEntity.ok(brandService.updateBrand(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable @Positive int id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandAdminDetailsDto> getBrand(@PathVariable @Positive int id) {
        return ResponseEntity.ok(brandService.getBrand(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BrandAdminSummaryDto>> getAllBrands(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        // setup pagination settings
        sort = sort.equalsIgnoreCase("date") ? "createdAt" : "name";
        Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, Math.min(size, 50), Sort.by(dir, sort));

        return ResponseEntity.ok(brandService.getAllBrands(search, active, pageable));
    }
}
