package com.mohsenko.e_commerce_mohsenko.entity;

import com.mohsenko.e_commerce_mohsenko.enums.PrivacyLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_return_status")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturnStatus extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "color", length = 50, unique = true, nullable = false)
    private String color;

    @Column(name = "description", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy", nullable = false)
    private PrivacyLevel privacy = PrivacyLevel.MODERATOR;

    @Column(name = "status_order", nullable = false, unique = true)
    private Short statusOrder;
}

