package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brand")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "icon_url", length = 500, unique = true)
    private String iconUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "slug", length = 50, nullable = false, unique = true)
    private String slug;
}
