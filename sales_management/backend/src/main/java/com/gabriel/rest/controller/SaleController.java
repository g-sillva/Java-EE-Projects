package com.gabriel.rest.controller;

import com.gabriel.rest.entity.DTO.CreateSaleDTO;
import com.gabriel.rest.entity.responses.ErrorResponse;
import com.gabriel.rest.entity.Sale;
import com.gabriel.rest.entity.responses.PaginationResponse;
import com.gabriel.rest.service.JWTService;
import com.gabriel.rest.service.SaleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sales")
public class SaleController {

    @Inject
    SaleService saleService;

    @Inject
    JWTService jwtService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSales(@HeaderParam("Authorization") String authorization,
                                @QueryParam("page") @DefaultValue("1") int page,
                                @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        System.out.println(jwtService);
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Invalid authorization token.", 403))
                    .build();
        }

        PaginationResponse<Sale> response = saleService.getAll(page, pageSize);
        return Response
                .status(Response.Status.OK)
                .entity(response)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaleById(@PathParam("id") Long id, @HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Invalid authorization token.", 403))
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
                    .entity(new ErrorResponse("Sale not found for id " + id, 404))
                    .build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSale(CreateSaleDTO saleDTO, @HeaderParam("Authorization") String authorization) {
        if (authorization == null || !jwtService.isTokenValid(authorization)) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Invalid authorization token.", 403))
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
                    .entity(new ErrorResponse("Unable to register sale", 400))
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
                    .entity(new ErrorResponse("Invalid authorization token.", 403))
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
                    .entity(new ErrorResponse("Sale not found for id " + id, 404))
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
                    .entity(new ErrorResponse("Invalid authorization token.", 403))
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
                    .entity(new ErrorResponse("Sale not found for id " + id, 404))
                    .build();
        }
    }
}
