package com.mohsenko.e_commerce_mohsenko.repository;

import com.mohsenko.e_commerce_mohsenko.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {
    Boolean existsBySlug(String slug);

    @Query("SELECT COUNT(b) > 0 FROM Brand b WHERE LOWER(b.name) = LOWER(:name)")
    Boolean existsByNameIgnoreCase(@Param("name") String name);

    @Query("SELECT COUNT(b) > 1 FROM Brand b WHERE b.slug = :slug")
    Boolean existsBySlugIgnoreCurrent(String slug);

    Optional<Brand> findBySlugAndIsActive(String slug, boolean isActive);
}
