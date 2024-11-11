package com.jux.familyspace.service;

import com.jux.familyspace.model.FamilyUser;
import com.jux.familyspace.repository.FamilyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final FamilyUserRepository familyUserRepository;


    public String loginAsGuest(String username) {
        FamilyUser guestUser = new FamilyUser();
        guestUser.setUsername(username);
        familyUserRepository.save(guestUser);

        return "Guest:" + username + ":" + UUID.randomUUID();
    }

    public String getGuestName(String authorizationHeader) {

        String token = authorizationHeader.split(" ")[1];
        String username = "";
        if (token != null && token.startsWith("Guest:")) {
            String[] parts = token.split(":");
            if (parts.length >= 3) {
                username = parts[1];

            }
        }
        return username;
    }
}
