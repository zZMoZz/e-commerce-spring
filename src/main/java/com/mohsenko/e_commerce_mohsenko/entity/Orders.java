package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
@Table(name = "orders")
@Getter @Setter
@ToString(exclude = {"status", "customer", "address", "coupon", "shippingPartner"})
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number", length = 50, unique = true, nullable = false)
    private String orderNumber;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "shipping_fee", precision = 10, scale = 2, nullable = false)
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "estimated_delivery_at")
    private OffsetDateTime estimatedDeliveryAt;

    @Column(name = "order_approved_at")
    private OffsetDateTime orderApprovedAt;

    @Column(name = "order_shipped_at")
    private OffsetDateTime orderShippedAt;

    @Column(name = "order_delivered_at")
    private OffsetDateTime orderDeliveredAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private OrderAddressSnapshot address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipping_partner_id", referencedColumnName = "id", nullable = false)
    private ShippingPartner shippingPartner;
}
