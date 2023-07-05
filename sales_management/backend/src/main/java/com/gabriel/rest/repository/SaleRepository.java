package com.gabriel.rest.repository;

import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.manager.JpaEntityManager;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

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
}
