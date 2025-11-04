package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_property_value")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPropertyValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value", length = 255, nullable = false)
    private String value;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_properties_id", referencedColumnName = "id", nullable = false)
    private CategoryProperties categoryProperties;
}
