package com.mohsenko.e_commerce_mohsenko.dto.brand;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mohsenko.e_commerce_mohsenko.deserializer.NormalizedStringDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrandCreateDto {
    @JsonDeserialize(using = NormalizedStringDeserializer.class)
    @NotBlank(message = "Brand name is required")
    @Size(min = 2, max = 50, message = "Brand name must be between 2 and 50 characters")
    private String name;

    @JsonDeserialize(using = NormalizedStringDeserializer.class)
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Size(max = 500, message = "Icon URL must not exceed 500 characters")
    private String iconUrl;

    private Boolean isActive = false;
}