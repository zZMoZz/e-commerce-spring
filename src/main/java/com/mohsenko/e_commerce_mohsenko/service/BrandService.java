package com.mohsenko.e_commerce_mohsenko.service;

import com.mohsenko.e_commerce_mohsenko.dto.brand.*;
import com.mohsenko.e_commerce_mohsenko.dto.pagination.PageResponse;
import com.mohsenko.e_commerce_mohsenko.entity.Brand;
import com.mohsenko.e_commerce_mohsenko.exception.DatabaseException;
import com.mohsenko.e_commerce_mohsenko.exception.DuplicateResourceException;
import com.mohsenko.e_commerce_mohsenko.exception.ResourceNotFoundException;
import com.mohsenko.e_commerce_mohsenko.mapper.BrandMapper;
import com.mohsenko.e_commerce_mohsenko.mapper.PageMapper;
import com.mohsenko.e_commerce_mohsenko.repository.BrandRepo;
import com.mohsenko.e_commerce_mohsenko.specification.BrandSpecifications;
import com.mohsenko.e_commerce_mohsenko.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepo brandRepo;
    private final BrandMapper brandMapper;

    @Transactional
    public BrandAdminDetailsDto createBrand(BrandCreateDto brandCreateDto) {
        // check duplicate entities
        if (brandRepo.existsByNameIgnoreCase(brandCreateDto.getName())) {
            throw new DuplicateResourceException("Brand", "name", brandCreateDto.getName().toLowerCase());
        }

        // map dto to entity
        Brand newBrand = brandMapper.toEntity(brandCreateDto);

        // create and assign a slug
        String slug = SlugUtil.toSlug(newBrand.getName(), brandRepo::existsBySlug);
        newBrand.setSlug(slug);

        // save, map, and return the entity
        try {
            return brandMapper.toBrandAdminDetailsDto(brandRepo.save(newBrand));
        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to create brand", ex);
        }
    }

    @Transactional
    public BrandAdminDetailsDto updateBrand(int id, BrandUpdateDto brandUpdateDto) {
        // check entity exists
        Brand brand = brandRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", String.valueOf(id)));

        if (brandUpdateDto.getName() != null && !brand.getName().equals(brandUpdateDto.getName())) {
            // check duplicate names
            if (brandRepo.existsByNameIgnoreCase(brandUpdateDto.getName())) {
                throw new DuplicateResourceException("Brand", "name", brandUpdateDto.getName().toLowerCase());
            }
            // update slug
            String slug = SlugUtil.toSlug(brandUpdateDto.getName(), brandRepo::existsBySlugIgnoreCurrent);
            brand.setSlug(slug);
        }

        // map dto to entity
        brandMapper.toEntity(brandUpdateDto, brand);

        // save, map, and return the entity
        try {
            return brandMapper.toBrandAdminDetailsDto(brandRepo.save(brand));
        } catch (DataAccessException ex) {
            throw new DatabaseException(String.format("Failed to update brand with id: %s", id), ex);
        }

    }

    @Transactional
    public void deleteBrand(Integer id) {
        // check entity exists
        Brand brand = brandRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", String.valueOf(id)));

        // delete it
        try {
            brandRepo.delete(brand);
        } catch (DataAccessException ex) {
            throw new DatabaseException(String.format("Failed to delete brand with id: %s", id), ex);
        }
    }

    @Transactional(readOnly = true)
    public BrandAdminDetailsDto getBrand(Integer id) {
        // check brand exists
        Brand brand = brandRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", String.valueOf(id)));

        // map and return
        return brandMapper.toBrandAdminDetailsDto(brand);
    }

    @Transactional(readOnly = true)
    public BrandUserDetailsDto getActiveBrand(String slug) {
        // check brand exists
        Brand brand = brandRepo.findBySlugAndIsActive(slug, true)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "slug", slug));

        // map and return
        return brandMapper.toBrandUserDetailsDto(brand);
    }

    @Transactional(readOnly = true)
    public PageResponse<BrandAdminSummaryDto> getAllBrands(String search, Boolean isActive, Pageable pageable) {
        // build a specification
        Specification<Brand> spec = Specification.allOf();

        if (search != null && !search.isBlank()) {
            spec = spec.and(BrandSpecifications.hasNameLike(search));
        }

        if (isActive != null) {
            spec = spec.and(BrandSpecifications.hasActiveStatus(isActive));
        }

        try {
            // find entities, and map entities to DTOs
            Page<BrandAdminSummaryDto> brands = brandRepo.findAll(spec, pageable)
                    .map(brandMapper::toBrandAdminSummaryDto);

            // reduce pagination response, and return
            return PageMapper.toPageResponse(brands);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to fetch brands", ex);
        }
    }

    @Transactional(readOnly = true)
    public PageResponse<BrandUserSummaryDto> getAllActiveBrands(String search, Pageable pageable) {
        // build a specification
        Specification<Brand> spec = BrandSpecifications.hasActiveStatus(true);

        if (search != null && !search.isBlank()) {
            spec = spec.and(BrandSpecifications.hasNameLike(search));
        }

        try {
            // find entities, and map entities to DTOs
            Page<BrandUserSummaryDto> brands = brandRepo.findAll(spec, pageable)
                    .map(brandMapper::toBrandUserSummaryDto);

            // reduce pagination response, and return
            return PageMapper.toPageResponse(brands);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Failed to fetch brands", ex);
        }
    }
}