package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wish_list_item")
@Getter @Setter
@ToString(exclude = {"wishList", "product"})
@NoArgsConstructor
@AllArgsConstructor
public class WishListItem {
    @EmbeddedId
    private WishListItemId id = new WishListItemId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("wishlistId")
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id", nullable = false)
    private WishList wishList;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}
