package com.gabriel.rest.service;

import com.gabriel.rest.entity.DTO.CreateUserDTO;
import com.gabriel.rest.entity.DTO.LoginUserDTO;
import com.gabriel.rest.entity.User;
import com.gabriel.rest.repository.UserRepository;
import com.sun.jersey.spi.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    @Inject
    UserRepository userRepository;

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
                return "";
            }
            return "Incorrect password. Try again.";
        }
        return "User not found with this email.";
    }
}
