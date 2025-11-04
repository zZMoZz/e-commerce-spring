package com.mohsenko.e_commerce_mohsenko.entity;

import com.mohsenko.e_commerce_mohsenko.enums.GenderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter @Setter
@ToString(exclude = {"customerAddresses"})
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 150, nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 20, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "email_verified_at")
    private OffsetDateTime emailVerifiedAt;

    @Column(name = "last_login_at")
    private OffsetDateTime lastLoginAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "slug", length = 150, nullable = false, unique = true)
    private String slug;

    // Relationships
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<CustomerAddress> customerAddresses;
}
