package org.example.repositories;

import org.example.entities.Accounts;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AccountsRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveAccount(Accounts account){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(account);

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
}
