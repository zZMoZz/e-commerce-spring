package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
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

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "parent_path", nullable = false)
    private String parentPath;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "slug", length = 50, nullable = false, unique = true)
    private String slug;
}

/* - check display_order >= 0 during inserting. */

