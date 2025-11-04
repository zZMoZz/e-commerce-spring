package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_properties")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "unit", length = 50)
    private String unit;

    @Column(name = "is_variant", nullable = false)
    private Boolean isVariant = false;

    @Column(name = "is_filterable", nullable = false)
    private Boolean isFilterable = false;

    @Column(name = "display_order", nullable = false)
    private Short displayOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private CategoryProperty categoryProperty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;
}
