package com.mohsenko.e_commerce_mohsenko.dto.brand;

import lombok.Data;

@Data
public class BrandAdminSummaryDto {
    private Integer id;
    private String name;
    private String slug;
    private String iconUrl;
    private Boolean isActive;
}
