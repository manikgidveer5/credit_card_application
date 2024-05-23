package com.example.Credit_Card_Application.service;

import com.example.Credit_Card_Application.exception.CardNotFoundException;
import com.example.Credit_Card_Application.exception.UserNotFoundException;
import com.example.Credit_Card_Application.exception.UserServiceException;
import com.example.Credit_Card_Application.model.User;
import com.example.Credit_Card_Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getUser(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new CardNotFoundException("User not found with ID: " + id);
        }
    }

    public String getUserByEmailAndPass(String email, String pass) {
        try {
            Optional<User> userPresent = userRepository.findByEmail(email);
            if (userPresent.isPresent() && passwordEncoder.matches(pass, userPresent.get().getPassword())) {
                return "User Present!!!";
            } else {
                throw new UserNotFoundException("Invalid credentials");
            }
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UserServiceException("Failed to validate user credentials");
        }
    }


    public String saveUser(User user) {
        try {
            Optional<User> userPresent = userRepository.findByEmail(user.getEmail());
            if (userPresent.isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return "User Saved Successfully!!!";
            } else {
                User existingUser = userPresent.get();
                existingUser.setUserName(user.getUserName());
                existingUser.setEmail(user.getEmail());
                existingUser.setContactNo(user.getContactNo());
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(existingUser);
                return "User Updated Successfully!!!";
            }
        } catch (Exception e) {
            throw new UserServiceException("Failed to save user");
        }
    }
}
