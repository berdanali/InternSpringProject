package com.demo1.demo.controller;

import com.demo1.demo.dto.UserDto;
import com.demo1.demo.service.Impl.TaskServiceImpl;
import com.demo1.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    @Autowired
    private TaskServiceImpl taskService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto user = userService.getUser(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticateUser(@RequestParam String mail, @RequestParam String password) {
        // E-posta formatını doğrulama
        if (!mail.matches("^[a-zA-Z0-9._%+-]+@universiteadi\\.edu\\.tr$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Geçersiz e-posta formatı
        }

        // Üniversite adını çıkarma
        String universityName = mail.substring(mail.indexOf('@') + 1, mail.indexOf('.'));

        // Kullanıcıyı doğrulama
        UserDto authenticatedUser = userService.authenticateUser(mail, password);
        if (authenticatedUser != null) {
            // Kullanıcının üniversite adını güncelle
            authenticatedUser.setUniversity(universityName);
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> loadUserByUsername(@PathVariable("username") String username) {
        try {
            UserDto user = userService.getUser(Long.valueOf(username));
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin"; // Thymeleaf template adı
    }
}
