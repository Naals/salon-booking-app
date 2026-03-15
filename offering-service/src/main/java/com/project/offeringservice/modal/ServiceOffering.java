package com.project.offeringservice.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long salonId;

    private String image;

}
