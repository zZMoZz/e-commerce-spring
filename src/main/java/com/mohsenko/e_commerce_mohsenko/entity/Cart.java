package com.mohsenko.e_commerce_mohsenko.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cart")
@Getter @Setter
@ToString(exclude = {"customer"})
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relationships
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, unique = true)
    private Customer customer;
}