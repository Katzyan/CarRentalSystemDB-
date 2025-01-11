package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Car car;

    @ManyToMany
    @JoinTable(
            name = "drivers_assigned",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private List<Driver> drivers = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate reservedFrom;
    @Column(nullable = false)
    private LocalDate reservedTo;

    private int totalPrice; // based on number of days, calculated from base price
}
