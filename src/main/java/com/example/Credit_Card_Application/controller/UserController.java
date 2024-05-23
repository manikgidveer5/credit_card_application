package com.example.Credit_Card_Application.controller;

import com.example.Credit_Card_Application.model.User;
import com.example.Credit_Card_Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/user/login")
    public Map<String, String> validUser(@RequestParam String email, @RequestParam String pass) {
        Map<String, String> map = new HashMap<>();
        try {
            userService.getUserByEmailAndPass(email, pass);
            map.put("Result", "Success");
        } catch (Exception e) {
            map.put("Result", "Failure");
            map.put("Error", e.getMessage());
        }
        return map;
    }

    @PostMapping("/user")
    public Map<String,String> saveUser(@RequestBody User user) {
        Map<String,String> map = new HashMap<>();
        System.out.println(user.getUserName());
        userService.saveUser(user);
        map.put("Result","Success");
        return map;
    }
}
