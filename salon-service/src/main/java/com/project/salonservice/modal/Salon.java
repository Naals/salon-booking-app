package com.project.salonservice.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    private List<String> images;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private LocalDateTime openTime;

    @Column(nullable = false)
    private LocalDateTime closeTime;
}
