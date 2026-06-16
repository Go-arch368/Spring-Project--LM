package com.library.management.service;


import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import com.library.management.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists!");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email alreaady exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Registered Successfully";
    }

    public String login(String username , String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        return jwtService.generateToken(username);
    }

}
