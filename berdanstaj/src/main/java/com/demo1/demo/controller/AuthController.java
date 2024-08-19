package com.demo1.demo.controller;

import com.demo1.demo.dto.UserDto;
import com.demo1.demo.entity.User;
import com.demo1.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        Optional<User> user = userRepository.findByMail(userDto.getMail());

        if (user.isPresent() && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            User loggedInUser = user.get();
            Map<String, String> response = new HashMap<>();
            response.put("fullname", loggedInUser.getFullname());
            response.put("role", loggedInUser.getRole());

            if ("Yönetici".equalsIgnoreCase(loggedInUser.getRole())) {
                response.put("redirectUrl", "/admin.html");
            } else {
                response.put("redirectUrl", "/user.html");
            }

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}