package com.mohsenko.e_commerce_mohsenko.specification;

import com.mohsenko.e_commerce_mohsenko.entity.Brand;
import org.springframework.data.jpa.domain.Specification;

public class BrandSpecifications {
    // search
    public static Specification<Brand> hasNameLike(String name) {
        return (root, query, cb) -> cb
                .like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase().replaceAll(" +", " ") + "%");
    }

    // only active or all
    public static Specification<Brand> hasActiveStatus(boolean status) {
        return (root, query, cb) -> cb
                .equal(root.get("isActive"), status);
    }
}
