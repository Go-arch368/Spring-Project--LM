package com.library.management.controller;


import com.library.management.model.User;
import com.library.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public String register(@RequestBody User user){
        return authService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> credentials){
        return authService.login(
                credentials.get("username"),
                credentials.get("password")
        );
    }

}
