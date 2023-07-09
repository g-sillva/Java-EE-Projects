package com.gabriel.rest.service;

import com.gabriel.rest.entity.DTO.CreateUserDTO;
import com.gabriel.rest.entity.DTO.LoginUserDTO;
import com.gabriel.rest.entity.User;
import com.gabriel.rest.repository.UserRepository;
import com.gabriel.rest.util.JWTUtils;
import io.jsonwebtoken.Jwts;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Stateless
public class UserService {

    UserRepository userRepository = new UserRepository();

    public User createUser(CreateUserDTO userDTO) {
        User user = new User();

        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(hashedPassword);

        return userRepository.create(user);
    }

    public String login(LoginUserDTO loginUserDTO) {
        User user = userRepository.getUserByEmail(loginUserDTO.getEmail());

        if (user != null) {
            if (BCrypt.checkpw(loginUserDTO.getPassword(), user.getPassword())) {

                Key hmacKey = JWTUtils.getKey();

                return Jwts.builder()
                        .claim("username", user.getUsername())
                        .claim("email", user.getEmail())
                        .setSubject(user.getUsername())
                        .setId(UUID.randomUUID().toString())
                        .signWith(hmacKey)
                        .setIssuedAt(Date.from(Instant.now()))
                        .setExpiration(Date.from(Instant.now().plus(20L, ChronoUnit.MINUTES)))
                        .compact();
            }
            return "Incorrect password. Try again.";
        }
        return "User not found with this email.";
    }
}
