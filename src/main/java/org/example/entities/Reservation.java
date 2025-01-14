package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.repositories.AccountsRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @ManyToMany()
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

    public static void showAllReservations(Accounts account){
        AccountsRepository accountsRepository = new AccountsRepository();
        Driver driver = accountsRepository.getDriverWithFullReservations(account);
        List<Reservation> reservs = driver.getReservations();
        Car car = new Car();

        reservationsHeader();

        for(Reservation res: reservs){
            List<Driver> drivers = new ArrayList<>();
            drivers = res.getDrivers();
            car = res.getCar();

            if(drivers.size() == 1){
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver.getName(), "N/A");
            } else {
                String driver1 = "N/A";
                String driver2= "N/A";
                for(Driver driverForName : drivers){
                    if(driverForName.getName().equals(driver.getName())){
                        driver1 = driver.getName();
                    } else {
                        driver2 = driverForName.getName();
                    }
                }
                System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   | %-20s  | %-20s%n",
                        car.getLicensePlate(), car.getMake() + " " + car.getModel(), car.getVehicleType(), res.getReservedFrom(), res.getReservedTo(),
                        ChronoUnit.DAYS.between(res.getReservedFrom(), res.getReservedTo()), driver1, driver2);
            }
        }



    }

    public static void reservationsHeader(){
        System.out.printf("%-10s |  %-20s  |  %-9s  |  %-14s  | %-14s  |  %-14s   |  %-20s  | %-20s%n",
                "Plate","Make and Model", "Type", "Reserved From", "Reserved To", "Number of Days", "Driver1", "Driver2");
    }
}




