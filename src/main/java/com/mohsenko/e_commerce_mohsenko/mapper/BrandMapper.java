package com.mohsenko.e_commerce_mohsenko.mapper;

import com.mohsenko.e_commerce_mohsenko.dto.brand.*;
import com.mohsenko.e_commerce_mohsenko.entity.Brand;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Brand toEntity(BrandCreateDto brandCreateDto);

    BrandUserSummaryDto toBrandUserSummaryDto(Brand brand);
    BrandAdminSummaryDto toBrandAdminSummaryDto(Brand brand);
    BrandUserDetailsDto toBrandUserDetailsDto(Brand brand);
    BrandAdminDetailsDto toBrandAdminDetailsDto(Brand brand);

    // It tells MapStruct if a field in the DTO is `null`, ignore it and don't update the corresponding field in the entity.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void toEntity(BrandUpdateDto brandUpdateDto, @MappingTarget Brand brand);
}
