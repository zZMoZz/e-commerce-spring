package com.mohsenko.e_commerce_mohsenko.entity;

import com.mohsenko.e_commerce_mohsenko.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "coupon")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 100, nullable = false, unique = true)
    private String code;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType = DiscountType.AMOUNT;

    @Column(name = "discount_value", precision = 10, scale = 2, nullable = false)
    private BigDecimal discountValue;

    @Column(name = "max_discount_value", precision = 10, scale = 2)
    private BigDecimal maxDiscountValue;

    @Column(name = "min_order_total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal minOrderTotalPrice;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "usage_per_customer_limit")
    private Short usagePerCustomerLimit;

    @Column(name = "valid_from")
    private OffsetDateTime validFrom;

    @Column(name = "valid_until")
    private OffsetDateTime validUntil;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;
}


