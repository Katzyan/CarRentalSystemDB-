package org.example.repositories;

import org.example.entities.Accounts;
import org.example.entities.Car;
import org.example.entities.Driver;
import org.example.entities.Reservation;
import org.example.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AccountsRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveAccount(Accounts account){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(account);

        session.getTransaction().commit();
        session.close();
    }

    public void updateAccount(Accounts account){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(account);

        session.getTransaction().commit();
        session.close();
    }

    public Accounts getAccountByUsername(String username){
        Session session = sessionFactory.openSession();
        Accounts account = session.createQuery("FROM Accounts WHERE username = :usernameParam", Accounts.class)
                        .setParameter("usernameParam", username)
                        .uniqueResult();
        session.close();
        return account;
    }

    public List<Accounts> getAllAccounts(){
        Session session = sessionFactory.openSession();
        String hql = "FROM Accounts";
        List<Accounts> accounts = session.createQuery(hql, Accounts.class).getResultList();
        session.close();
        return  accounts;
    }

    public List<Accounts> getAdminAccounts(){
        Session session = sessionFactory.openSession();
        String hql = "FROM Accounts a WHERE a.isAdmin = true";
        List<Accounts> adminAccounts = session.createQuery(hql, Accounts.class).getResultList();
        session.close();
        return adminAccounts;
    }

    public List<Accounts> getUserAccounts(){
        Session session = sessionFactory.openSession();
        String hql = "FROM Accounts a WHERE a.isAdmin = false";
        List<Accounts> userAccounts = session.createQuery(hql, Accounts.class).getResultList();
        session.close();
        return userAccounts;
    }

    public void deleteAccount(Accounts account){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete((account));

        session.getTransaction().commit();
        session.close();
    }

    public Driver getDriverWithFullReservations(Accounts account) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        account = (Accounts)session.merge(account);
        Driver driver = account.getDriver();
//        driver = (Driver)session.merge(driver);
        Hibernate.initialize(driver.getReservations());
       for(Reservation res : driver.getReservations()){
           Hibernate.initialize(res.getDrivers());
       }

        session.getTransaction().commit();
        session.close();
        return driver;
    }
}
