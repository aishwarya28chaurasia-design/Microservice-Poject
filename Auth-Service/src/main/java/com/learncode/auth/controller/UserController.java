package com.learncode.auth.controller;

import com.learncode.auth.exception.BadRequestException;
import com.learncode.auth.model.dto.JWTTokenResponse;
import com.learncode.auth.model.dto.LoginRequest;
import com.learncode.auth.model.dto.UserDto;
import com.learncode.auth.model.entity.User;
import com.learncode.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user) {
        UserDto userDto = userService.register(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/generate-token")
    public JWTTokenResponse generateToken(@RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if(authentication.isAuthenticated()){
                return userService.generateToken(loginRequest.getUsername());
            }else {
                throw new BadRequestException("Invalid username or password");
            }
        } catch (Exception e) {
            throw new BadRequestException("Invalid username or password");
        }
    }
}
