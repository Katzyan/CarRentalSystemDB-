package org.example.repositories;

import org.example.entities.Driver;
import org.example.entities.Reservation;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReservationRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveReservation(Reservation reservation) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(reservation);

        session.getTransaction().commit();
        session.close();
    }

}
