package com.gabriel.rest.service;

import com.gabriel.rest.util.JWTUtils;

import javax.ejb.Stateless;

public class JWTService {

    public boolean isTokenValid(String token) {
        token = token.replace("Bearer ", "");
        return JWTUtils.validateToken(token);
    }
}
