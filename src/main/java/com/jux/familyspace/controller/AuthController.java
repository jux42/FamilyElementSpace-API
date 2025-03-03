package com.jux.familyspace.controller;

import com.jux.familyspace.service.AuthService;
import com.jux.familyspace.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final GuestService guestService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(authService.authenticate(username, password));
    }

    @PostMapping("/memberregister")
    public ResponseEntity<String> memberRegister(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(authService.register(username, password, true));
    }

    @PostMapping("/guestlogin")
    public ResponseEntity<String> loginAsGuest(@RequestParam String username) {
        return ResponseEntity.ok(guestService.loginAsGuest(username));
    }

    @GetMapping("/getname")
    public ResponseEntity<String> getGuestName(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @GetMapping("/whoami")
    public ResponseEntity<String> getPrincipal(Principal principal) {
        return ResponseEntity.ok(principal.toString());
    }


}