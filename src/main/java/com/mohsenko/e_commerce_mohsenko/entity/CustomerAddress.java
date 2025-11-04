package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@Entity
@Table(name = "customer_address")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddress extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "phone_backup", length = 20)
    private String phoneBackup;

    @Column(name = "country", length = 100, nullable = false)
    private String country;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "street", length = 255, nullable = false)
    private String street;

    @Column(name = "building_name", length = 100)
    private String buildingName;


    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    // Relationships
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
}
