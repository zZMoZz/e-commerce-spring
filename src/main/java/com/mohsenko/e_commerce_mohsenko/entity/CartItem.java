package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseEntity {
    @EmbeddedId
    private CartItemId id = new CartItemId();

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("variantId")
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false)
    private ProductVariant variant;
}