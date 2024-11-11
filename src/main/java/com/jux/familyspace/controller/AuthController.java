package com.jux.familyspace.controller;

import com.jux.familyspace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.authenticate(username, password);
    }

    @PostMapping("/userregister")
    public void userRegister(@RequestParam String username, @RequestParam String password) {
        authService.register(username, password);
    }

    @PostMapping("/memberregister")
    public void memberRegister(@RequestParam String username, @RequestParam String password) {
        authService.register(username, password);
    }
}