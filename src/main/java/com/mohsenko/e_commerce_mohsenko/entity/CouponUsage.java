package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "coupon_usage")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponUsage extends BaseEntity {
    @EmbeddedId
    private CouponUsageId id = new CouponUsageId();

    @Column(name = "usage_count", nullable = false)
    private Short usageCount = 1;

    @Column(name = "latest_time", nullable = false)
    private OffsetDateTime latestTime = OffsetDateTime.now();

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("couponId")
    @JoinColumn(name = "coupon_id", referencedColumnName = "id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
}
