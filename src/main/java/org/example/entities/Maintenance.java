package org.example.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate maintenanceStart;
    private LocalDate maintenanceEnd;

    @Transient
    private List<String> maintenanceDetailsList = new ArrayList<>();

    @Lob
    private String maintenanceDetails;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Car car;

}
