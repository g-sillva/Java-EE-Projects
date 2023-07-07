package com.gabriel.rest.controller;

import com.gabriel.rest.entity.DTO.CreateSaleDTO;
import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.service.JWTService;
import com.gabriel.rest.service.SaleService;
import com.gabriel.rest.util.JWTUtils;
import com.sun.jersey.spi.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/sales")
public class SaleController {

    @Inject
    SaleService saleService;

    @Inject
    JWTService jwtService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSales(@HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("Invalid authorization token.")
                    .build();
        }

        List<Sale> resultSales = saleService.getAll();

        return Response
                .status(Response.Status.OK)
                .entity(resultSales)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaleById(@PathParam("id") Long id, @HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("Invalid authorization token.")
                    .build();
        }

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
    public Response createSale(CreateSaleDTO saleDTO, @HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("Invalid authorization token.")
                    .build();
        }

        Sale sale = saleService.createSale(saleDTO);

        if (sale != null) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(sale)
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Unable to register sale")
                    .build();
        }


    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSale(@PathParam("id") Long id, CreateSaleDTO body, @HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("Invalid authorization token.")
                    .build();
        }

        Sale sale = saleService.updateSale(id, body);
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

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSalte(@PathParam("id") Long id, @HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("Invalid authorization token.")
                    .build();
        }

        Sale sale = saleService.deleteSale(id);
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
}
