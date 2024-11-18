package com.jux.familyspace.service;


import com.jux.familyspace.api.FamilyMemberElementDtoMapperInterface;
import com.jux.familyspace.model.*;
import com.jux.familyspace.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyMemberElementDtoMapperInterface<Haiku> haikuProxy;
    private final FamilyMemberElementDtoMapperInterface<DailyThought> dailyProxy;
    private final FamilyMemberElementDtoMapperInterface<FamilyMemoryPicture> memoryPicsProxy;


    public FamilyMember getCurrentUserByName(String username) {
        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);
        return familyMember.orElse(null);
    }

    public FamilyMemberOneTypeDto getMemberHaikuDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return haikuProxy.getMemberDto(familyMember);
    }

    public FamilyMemberOneTypeDto getMemberDailyThoughtsDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return dailyProxy.getMemberDto(familyMember);
    }

    public FamilyMemberOneTypeDto getMemberMemoryPicsDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return memoryPicsProxy.getMemberDto(familyMember);
    }

    }
