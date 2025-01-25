package org.example.repositories;

import org.example.entities.Car;
import org.example.entities.Maintenance;
import org.example.entities.Reservation;
import org.example.enums.GasType;
import org.example.enums.VehicleType;
import org.example.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveCar(Car car) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(car);

        session.getTransaction().commit();
        session.close();
    }

    public void updateCar(Car car) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(car);

        session.getTransaction().commit();
        session.close();
    }

    public List<Car> getAllCars() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Car";
        List<Car> cars = session.createQuery(hql, Car.class).getResultList();
        session.close();
        return cars;
    }

    public List<Car> getAvailableCars(LocalDate reservedFrom, LocalDate reservedUntil) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Car";
        List<Car> cars = session.createQuery(hql, Car.class).getResultList();
        List<Car> availableCars = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();
        List<Maintenance> maintenances = new ArrayList<>();
        for (Car car : cars) {
            boolean isAvailableReserve = true;
            boolean isAvailablemaint = true;
            reservations = car.getReservations();
            maintenances = car.getMaintenances();
            for (Reservation reservation : reservations) {
                if (!(reservation.getReservedTo().isBefore(reservedFrom) || reservation.getReservedFrom().isAfter(reservedUntil))) {
                    isAvailableReserve = false;
                    break;
                }
            }
            for(Maintenance maintenance : maintenances){
                if(!(maintenance.getMaintenanceStart().isBefore(reservedFrom) || maintenance.getMaintenanceEnd().isAfter(reservedUntil))){
                    isAvailablemaint = false;
                    break;
                }
            }
            if (isAvailableReserve && isAvailablemaint) {
                availableCars.add(car);
            }
        }
        session.close();
        return availableCars;
    }

    public Car getCarByLicensePlateWithReservations(String license) {
        Session session = sessionFactory.openSession();
        Car car = session.createQuery("FROM Car WHERE licensePlate = :licenseParam", Car.class)
                .setParameter("licenseParam", license)
                .uniqueResult();
        if (car != null) {
            Hibernate.initialize(car.getReservations());
            Hibernate.initialize(car.getMaintenances());
        }
        session.close();
        return car;
    }

    public List<Car> getAllCarsWithReservations() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c JOIN FETCH c.reservations";
        List<Car> cars = session.createQuery(hql, Car.class).getResultList();
        session.close();
        return cars;
    }

    public List<Car> getCarsByVehicleType(VehicleType vehicleType) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c WHERE c.vehicleType = :vehicleTypeParam";
        List<Car> cars = session.createQuery(hql, Car.class)
                .setParameter("vehicleTypeParam", vehicleType)
                .getResultList();
        session.close();
        return cars;
    }

    public List<Car> getCarsByTransmission(boolean isAuto) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c WHERE c.automatic = :vehicleTransParam";
        List<Car> cars = session.createQuery(hql, Car.class)
                .setParameter("vehicleTransParam", isAuto)
                .getResultList();
        session.close();
        return cars;
    }

    public List<Car> getCarsByFuelType(GasType fuelType) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c WHERE c.gasType = :fuelTypeParam";
        List<Car> cars = session.createQuery(hql, Car.class)
                .setParameter("fuelTypeParam", fuelType)
                .getResultList();
        session.close();
        return cars;
    }

    public Car getCarByID(int id) {
        Session session = sessionFactory.openSession();
        Car car = session.get(Car.class, id);
        session.close();

        return car;
    }

    public Car getCarByVIN(String vin) {
        Session session = sessionFactory.openSession();
        Car car = session.createQuery("FROM Car WHERE VIN = :vinParam", Car.class)
                .setParameter("vinParam", vin)
                .uniqueResult();
        session.close();

        return car;
    }

    public Car getCarByLicensePlate(String license) {
        Session session = sessionFactory.openSession();
        Car car = session.createQuery("FROM Car WHERE licensePlate = :licenseParam", Car.class)
                .setParameter("licenseParam", license)
                .uniqueResult();
        session.close();
        return car;
    }


    public void deleteCar(Car car) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(car);

        session.getTransaction().commit();
        session.close();
    }

    public void deleteCarByVIN(String vin) {
        Car car = getCarByVIN(vin);
        deleteCar(car);
    }


}
