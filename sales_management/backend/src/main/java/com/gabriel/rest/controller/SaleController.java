package com.gabriel.rest.controller;

import com.gabriel.rest.entity.DTO.CreateSaleDTO;
import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.service.SaleService;
import com.sun.jersey.spi.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/sales")
public class SaleController {

    @Inject
    SaleService saleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSales() {
        List<Sale> resultSales = saleService.getAll();
        return Response
                .status(Response.Status.OK)
                .entity(resultSales)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaleById(@PathParam("id") Long id) {
        Sale sale = saleService.findById(id);
        if (sale != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(sale)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Sale not found for id " + id)
                    .build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSale(CreateSaleDTO sale) {
        Object response = saleService.createSale(sale);
        return Response
                .status(Response.Status.OK)
                .build();
    }
}
