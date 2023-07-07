package com.gabriel.rest.repository;

import com.gabriel.rest.entity.User;
import com.gabriel.rest.manager.JpaEntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;

public class UserRepository {

    private JpaEntityManager jpaEntityManager = new JpaEntityManager("primary");
    private EntityManager objEM = jpaEntityManager.getEntityManager();

    public User create(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = objEM.unwrap(Session.class);
            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new WebApplicationException(500);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
