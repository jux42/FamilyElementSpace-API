package com.jux.familyspace.service;


import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMember getCurrentUserByName(String username) {
        Optional<FamilyMember> familyMember =  familyMemberRepository.getByUsername(username);
        return familyMember.orElse(null);
    }
}
