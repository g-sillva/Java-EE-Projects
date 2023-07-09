package com.gabriel.rest.service;

import com.gabriel.rest.util.JWTUtils;

public class JWTService {

    public JWTService() {
    }

    public boolean isTokenValid(String token) {
        token = token.replace("Bearer ", "");
        return JWTUtils.validateToken(token);
    }
}
