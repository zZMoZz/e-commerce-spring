package com.mohsenko.e_commerce_mohsenko.entity;

import com.mohsenko.e_commerce_mohsenko.enums.PaymentMethod;
import com.mohsenko.e_commerce_mohsenko.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payment")
@Getter @Setter
@ToString(exclude = {"order"})
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "transaction_id", unique = true, length = 255)
    private String transactionId;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "paid_at")
    private OffsetDateTime paidAt;

    @Column(name = "failed_reason", columnDefinition = "TEXT")
    private String failedReason;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
}
