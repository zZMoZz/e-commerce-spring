package com.mohsenko.e_commerce_mohsenko.entity;

import com.mohsenko.e_commerce_mohsenko.enums.PaymentMethod;
import com.mohsenko.e_commerce_mohsenko.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "refund_transaction")
@Getter @Setter
@ToString(exclude = {"order", "orderReturn"})
@AllArgsConstructor
@NoArgsConstructor
public class RefundTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "refund_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal refundAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "transaction_id", length = 255, unique = true)
    private String transactionId;

    @Column(name = "paid_at")
    private OffsetDateTime paidAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "return_request_id", referencedColumnName = "id", nullable = false)
    private OrderReturn orderReturn;
}
