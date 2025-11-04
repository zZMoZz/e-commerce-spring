package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_variant_attribute")
@Getter @Setter
@ToString(exclude = {"productVariant", "categoryProperties", "categoryPropertyValue"})
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantAttribute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "property_text", length = 255)
    private String propertyText;

    @Column(name = "value_text", length = 255)
    private String valueText;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_properties_id", referencedColumnName = "id")
    private CategoryProperties categoryProperties;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id", referencedColumnName = "id")
    private CategoryPropertyValue categoryPropertyValue;
}
