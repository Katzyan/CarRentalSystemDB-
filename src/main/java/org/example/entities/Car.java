package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.GasType;
import org.example.enums.VehicleType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false, unique = true)
    private String vin;

    private String make;
    private String model;
    private LocalDate year;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;
    @Column(nullable = false)
    private boolean automatic;

    @Enumerated(EnumType.STRING)
    private GasType gasType;

    private int pricePerDay;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

    public void vehicleDisplay(){
        System.out.println("License plate: " + this.getLicensePlate());
        System.out.println("VIN: " + this.getVin());
        System.out.println("Make and Model: " + this.getMake() + " " + this.getModel());
        System.out.println("Manufacturing year: " + this.getYear());
        System.out.println("Vehicle Type: " + this.getVehicleType());
        if(isAutomatic()){
            System.out.println("Transmission: Automatic");
        } else {
            System.out.println("Transmission: Manual");
        }

        System.out.println("Fuel Type: " + this.getGasType());
        System.out.println("Price per Day: " + this.getPricePerDay());
    }

    public void vehicleDisplayAll(){
        String transmissionDispaly;
        if(this.isAutomatic())
            transmissionDispaly = "Automatic";
        else
            transmissionDispaly = "Manual";
        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-4s  | %-12s  |  %-8s  | %-12s%n", this.getLicensePlate(),this.getMake() + " " +
                this.getModel(), this.getVehicleType(), this.getYear().getYear(), transmissionDispaly, this.getGasType(), this.getPricePerDay());
    }

    public static void carTableHeader(){
        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-4s  | %-12s  |  %-8s  | %-12s%n",  "Plate","Make and Model", "Type", "Year", "Transmission", "Fuel", "Price per Day");
    }

}
