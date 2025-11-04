package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipping_partner")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShippingPartner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phone;

    @Column(name = "website", length = 255, nullable = false, unique = true)
    private String website;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
}
