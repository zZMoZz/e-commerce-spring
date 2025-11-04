package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WishListItemId implements Serializable {
    private Long wishListId;
    private Long productId;
}
