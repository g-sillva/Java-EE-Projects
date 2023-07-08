package com.gabriel.rest.repository;

import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.entity.User;
import com.gabriel.rest.manager.JpaEntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.WebApplicationException;

@Stateless
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

    public User getUserByEmail(String userEmail) {
        try {
            Query query = objEM.createNativeQuery("SELECT * FROM User WHERE email = ?", User.class);
            query.setParameter(1, userEmail);
            Object result = query.getSingleResult();

            if (result instanceof User) {
                User user = (User) result;
                objEM.close();
                return user;
            }
            return null;
        } catch (
                NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(500);
        }
    }
}
