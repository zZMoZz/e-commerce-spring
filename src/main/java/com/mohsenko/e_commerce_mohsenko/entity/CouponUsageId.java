package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CouponUsageId implements Serializable {
    private Long couponId;
    private Long customerId;
}
