package org.example.repositories;

import org.example.entities.Car;
import org.example.enums.GasType;
import org.example.enums.VehicleType;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CarRepository {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveCar(Car car){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(car);

        session.getTransaction().commit();
        session.close();
    }

    public void updateCar(Car car){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(car);

        session.getTransaction().commit();
        session.close();
    }

    public List<Car> getAllCars(){
        Session session = sessionFactory.openSession();
        String hql = "FROM Car";
        List<Car> cars = session.createQuery(hql, Car.class).getResultList();
        session.close();
        return  cars;
    }

    public List<Car> getCarsByVehicleType(VehicleType vehicleType){
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c WHERE c.vehicleType = :vehicleTypeParam";
        List<Car> cars = session.createQuery(hql, Car.class)
                .setParameter("vehicleTypeParam", vehicleType)
                .getResultList();
        session.close();
        return  cars;
    }
    public List<Car> getCarsByTransmission(boolean isAuto){
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c WHERE c.automatic = :vehicleTransParam";
        List<Car> cars = session.createQuery(hql, Car.class)
                .setParameter("vehicleTransParam", isAuto)
                .getResultList();
        session.close();
        return  cars;
    }

    public List<Car> getCarsByFuelType(GasType fuelType){
        Session session = sessionFactory.openSession();
        String hql = "FROM Car c WHERE c.gasType = :fuelTypeParam";
        List<Car> cars = session.createQuery(hql, Car.class)
                .setParameter("fuelTypeParam", fuelType)
                .getResultList();
        session.close();
        return  cars;
    }

    public Car getCarByID(int id){
        Session session = sessionFactory.openSession();
        Car car = session.get(Car.class, id);
        session.close();

        return car;
    }

    public Car getCarByVIN(String vin){
        Session session = sessionFactory.openSession();
        Car car = session.createQuery("FROM Car WHERE VIN = :vinParam", Car.class)
                        .setParameter("vinParam", vin)
                        .uniqueResult();
        session.close();

        return car;
    }

    public Car getCarByLicensePlate(String license){
        Session session = sessionFactory.openSession();
        Car car = session.createQuery("FROM Car WHERE licensePlate = :licenseParam", Car.class)
                .setParameter("licenseParam", license)
                .uniqueResult();
        session.close();
        return car;
    }

    public void deleteCar(Car car){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(car);

        session.getTransaction().commit();
        session.close();
    }

    public void deleteCarByVIN(String vin){
        Car car = getCarByVIN(vin);
        deleteCar(car);
    }



}
