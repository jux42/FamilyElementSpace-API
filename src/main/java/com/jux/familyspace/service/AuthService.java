package com.jux.familyspace.service;

import com.jux.familyspace.component.JwtUtil;
import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.model.FamilyUser;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyUserRepository;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final FamilyUserRepository familyUserRepository;
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
    public void register(String username, String password, Boolean isFamily) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        Path imagePath = Paths.get("src/main/resources/defaultpic/default_avatar.webp");
        byte[] image = Files.readAllBytes(imagePath); // Lire les octets de l'image
        FamilyMember familyMember = FamilyMember.builder()
                .tagline("i am new here")
                .avatar(image)
                .build();
        familyMember.setRole("MEMBER");
        familyMember.setUsername(username);
        familyMember.setPassword(hashedPassword);
        familyMemberRepository.save(familyMember);
    }
}
