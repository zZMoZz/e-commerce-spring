package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "product_tag")
@Getter @Setter
@ToString(exclude = {"products"})
@AllArgsConstructor
@NoArgsConstructor
public class ProductTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 150, nullable = false, unique = true)
    private String name;

    @Column(name = "icon_url", length = 500, unique = true)
    private String iconUrl;

    @Column(name = "display_order", nullable = false)
    private Short displayOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "slug", length = 150, nullable = false, unique = true)
    private String slug;

    @ManyToMany(mappedBy = "productTags", fetch = FetchType.LAZY)
    private Set<Product> products;
}
