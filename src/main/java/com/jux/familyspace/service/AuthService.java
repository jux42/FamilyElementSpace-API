package com.jux.familyspace.service;
import com.jux.familyspace.component.JwtUtil;
import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.model.FamilyUser;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyUserRepository;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final FamilyUserRepository familyUserRepository;
    private final FamilyMemberRepository familyMemberRepository;


    public String authenticate(String username, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        FamilyUser user = familyUserRepository.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // Génération du token JWT
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public void register(String username, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        FamilyUser familyUser = new FamilyUser();
        familyUser.setUsername(username);
        familyUser.setPassword(hashedPassword);
        familyUserRepository.save(familyUser);
    }

    @PrePersist
    public void register(String username, String password, Boolean isFamily) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        File file = new File("src/main/resources/defaultpic/default_avatar.webp");
        byte[] image = file.getPath().getBytes();
        FamilyMember familyMember = FamilyMember.builder()
                .tagline("i am new here")
                .avatar(image)
                .build();
        familyMember.setUsername(username);
        familyMember.setPassword(hashedPassword);
        familyMemberRepository.save(familyMember);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
