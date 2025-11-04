package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_specification")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecification extends BaseEntity {
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
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_properties_id", referencedColumnName = "id")
    private CategoryProperties categoryProperties;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id", referencedColumnName = "id")
    private CategoryPropertyValue categoryPropertyValue;
}
