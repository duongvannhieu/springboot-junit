package com.example.demo.service;

import com.example.demo.db.User;
import com.example.demo.db.UserRole;
import com.example.demo.db.request.LoginRequest;
import com.example.demo.db.request.RegisterUserRequest;
import com.example.demo.db.response.LoginResponse;
import com.example.demo.db.response.RegisterUserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setUsername(request.getUsername());
        response.setEmail(request.getEmail());
        if (request.getUsername().isEmpty() || request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            response.setMsg(Msg.USERNAME_EMAIL_PASSWORD_IS_NOT_EMPTY);
            return response;
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            response.setMsg(Msg.USERNAME_EXISTS);
            return response;
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            response.setMsg(Msg.EMAIL_EXISTS);
            return response;
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder().encode(request.getPassword()));
        user.setRole(UserRole.USER.name());
        userRepository.save(user);
        response.setMsg(Msg.REGISTER_USER_SUCCESS);
        return response;
    }

    public LoginResponse loginUser(LoginRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        LoginResponse response = new LoginResponse();
        response.setUsername(request.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder().matches(request.getPassword(), user.get().getPassword())) {
                response.setJwt(JwtToken.createJwtSingedHMAC(Long.toString(user.get().getId())));
                return response;
            }
        }
        response.setJwt("Login Fail");
        return response;
    }

}
