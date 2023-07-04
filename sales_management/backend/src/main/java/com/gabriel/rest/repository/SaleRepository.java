package com.gabriel.rest.repository;

import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.manager.JpaEntityManager;
import com.sun.jersey.spi.inject.Inject;

import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import java.util.List;

public class SaleRepository {

    private JpaEntityManager JPAEM = new JpaEntityManager();
    private EntityManager objEM = JPAEM.getEntityManager();

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
}
