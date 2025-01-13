package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isAdmin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public void viewAllAccounts(){
       String admin;
        if(this.isAdmin){
            admin = "Y";
        } else {
            admin = "N";
        }
        System.out.printf("%-10s | %-15s | %-10s | %-5s%n",this.getDriver().getName(), this.getDriver().getLicenseNumber(),
                this.getUsername(), admin);

    }
    public static void accountsTableHeader(){
        System.out.printf("%-10s | %-15s | %-10s | %-5s%n", "Name", "License Number", "Username", "Admin");
    }


}
