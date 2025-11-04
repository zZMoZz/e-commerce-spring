package com.mohsenko.e_commerce_mohsenko.entity;

import com.mohsenko.e_commerce_mohsenko.enums.CategoryPropertyDataType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_property")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProperty extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_datatype", nullable = false)
    private CategoryPropertyDataType valueDatatype = CategoryPropertyDataType.STRING;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;
}
