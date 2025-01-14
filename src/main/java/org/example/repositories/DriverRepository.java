package org.example.repositories;

import org.example.entities.Accounts;
import org.example.entities.Car;
import org.example.entities.Driver;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DriverRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(driver);

        session.getTransaction().commit();
        session.close();
    }

    public void updateDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(driver);

        session.getTransaction().commit();
        session.close();
    }


}
