package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product_variant")
@Getter @Setter
@ToString(exclude = {"product", "productVariantAttributes"})
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "sku", length = 255, unique = true, nullable = false)
    private String sku;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ProductVariantAttribute> productVariantAttributes;
}