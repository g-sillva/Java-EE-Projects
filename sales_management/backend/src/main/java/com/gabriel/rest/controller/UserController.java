package com.gabriel.rest.controller;

import com.gabriel.rest.entity.DTO.CreateUserDTO;
import com.gabriel.rest.entity.responses.ErrorResponse;
import com.gabriel.rest.entity.responses.TokenResponse;
import com.gabriel.rest.entity.DTO.LoginUserDTO;
import com.gabriel.rest.entity.User;
import com.gabriel.rest.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/users")
@Stateless
public class UserController {

    UserService userService = new UserService();

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
                    .entity(new ErrorResponse("Unable to register user", 400))
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginUserDTO loginUserDTO) {
        String res = userService.login(loginUserDTO);
        
        if (res.equals("Incorrect password. Try again.") ||
        	res.equals("User not found with this email.")) {
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorResponse(res, 403))
                    .build();
        }

        return Response
                .status(Status.OK)
                .entity(new TokenResponse(res))
                .build();
    }
}
