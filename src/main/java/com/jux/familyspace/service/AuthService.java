package com.jux.familyspace.service;

import com.jux.familyspace.component.JwtUtil;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.repository.FamilyMemberRepository;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final FamilyMemberRepository familyMemberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public String authenticate(String username, String password) {
        FamilyMember user = familyMemberRepository.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


    @PrePersist
    public String register(String username, String password, Boolean isFamily) {
        if (familyMemberRepository.getByUsername(username).isPresent()) return "user with this name already exists !";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        byte[] image = new byte[0];
        try {
            Path imagePath = Paths.get("src/main/resources/defaultpic/default_avatar.jpeg");
            image = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            log.error(e.getMessage());
            image = "no image".getBytes();
        }

        FamilyMember familyMember = FamilyMember.builder()
                .tagline("i am new here")
                .avatar(image)
                .build();
        familyMember.setRole("MEMBER");
        familyMember.setUsername(username);
        familyMember.setPassword(hashedPassword);
        familyMemberRepository.save(familyMember);

        return "user " + familyMember.getUsername() + " registered successfully";
    }


}
