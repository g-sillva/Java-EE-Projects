package com.gabriel.rest.controller;

import com.gabriel.rest.entity.DTO.CreateUserDTO;
import com.gabriel.rest.entity.User;
import com.gabriel.rest.service.UserService;
import com.sun.jersey.spi.inject.Inject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserDTO userDTO) {
        User user = userService.createUser(userDTO);
        if (user != null) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(user)
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Unable to register user")
                    .build();
        }

    }
}
