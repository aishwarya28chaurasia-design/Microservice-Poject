package com.learncode.auth.service;

import com.learncode.auth.model.dto.JWTTokenResponse;
import com.learncode.auth.model.dto.UserDto;
import com.learncode.auth.model.entity.User;
import com.learncode.auth.repository.UserRepository;
import com.learncode.auth.utility.JWTUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public UserDto register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(),savedUser.getUsername(),
                savedUser.getEmail(), savedUser.getRoles());


    }

    public JWTTokenResponse generateToken(String username) {
        String token = jwtUtils.generateToken(username);
        JWTTokenResponse jwtTokenResponse = new JWTTokenResponse();
        jwtTokenResponse.setToken(token);
        jwtTokenResponse.setType("Bearer");
        jwtTokenResponse.setValidUntil(jwtUtils.extractExpiration(token).toString());
        return jwtTokenResponse;
    }
}
