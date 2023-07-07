package com.gabriel.rest.repository;

import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.manager.JpaEntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.WebApplicationException;
import java.util.List;

public class SaleRepository {

    private JpaEntityManager jpaEntityManager = new JpaEntityManager("primary");
    private EntityManager objEM = jpaEntityManager.getEntityManager();

    public List<Sale> findAll() {
        try {
            String sql = "SELECT c FROM Sale c";
            List<Sale> sales = objEM.createQuery(sql, Sale.class).getResultList();
            objEM.close();
            return sales;
        } catch (Exception e) {
            throw new WebApplicationException(500);
        }
    }

    public Sale findById(Long id) {
        try {
            Query query = objEM.createNativeQuery("SELECT * FROM Sale WHERE id = ?", Sale.class);
            query.setParameter(1, id);
            Object result = query.getSingleResult();

            if (result instanceof Sale) {
                Sale sale = (Sale) result;
                objEM.close();
                return sale;
            }
            return null;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(500);
        }
    }

    public Sale create(Sale sale) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = objEM.unwrap(Session.class);
            transaction = session.beginTransaction();

            session.persist(sale);

            transaction.commit();
            return sale;
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

    public Sale update(Long id, Sale updatedSale) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = objEM.unwrap(Session.class);
            transaction = session.beginTransaction();

            Sale sale = (Sale) session.get(Sale.class, id);
            if (sale == null) {
                return null;
            }

            if (updatedSale.getTitle() != null) sale.setTitle(updatedSale.getTitle());
            if (updatedSale.getDescription() != null) sale.setDescription(updatedSale.getDescription());
            if (updatedSale.getValue() != null) sale.setValue(updatedSale.getValue());
            if (updatedSale.getDate() != null) sale.setDate(updatedSale.getDate());

            session.persist(sale);

            transaction.commit();
            return sale;
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

    public Sale delete(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = objEM.unwrap(Session.class);
            transaction = session.beginTransaction();

            Sale sale = (Sale) session.get(Sale.class, id);
            if (sale == null) {
                return null;
            }
            
            session.delete(sale);

            transaction.commit();
            return sale;
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
