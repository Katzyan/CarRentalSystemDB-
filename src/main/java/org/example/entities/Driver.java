package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String licenseNumber;


    @OneToOne(mappedBy = "driver")
    private Accounts accounts;


    @ManyToMany(mappedBy = "drivers")
    private List<Reservation> reservations = new ArrayList<>();

}
