package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemId implements Serializable {
    // we don't use @Column here because we already named
    // these columns in CartItem using @JoinColumn
    private Long cartId;
    private Long variantId;
}
