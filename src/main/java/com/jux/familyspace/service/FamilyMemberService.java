package com.jux.familyspace.service;


import com.jux.familyspace.api.FamilyMemberDtoMapperInterface;
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
    private final FamilyMemberDtoMapperInterface<Haiku> haikuDtoMapper;
    private final FamilyMemberDtoMapperInterface<DailyThought> dailyDtoMapper;
    private final FamilyMemberDtoMapperInterface<FamilyMemoryPicture> memoryPicsDtoMapper;


    public FamilyMember getCurrentUserByName(String username) {
        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);
        return familyMember.orElse(null);
    }

    public FamilyMemberOneTypeDto getMemberHaikuDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return haikuDtoMapper.getMemberDto(familyMember);
    }

    public FamilyMemberOneTypeDto getMemberDailyThoughtsDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return dailyDtoMapper.getMemberDto(familyMember);
    }

    public FamilyMemberOneTypeDto getMemberMemoryPicsDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return memoryPicsDtoMapper.getMemberDto(familyMember);
    }

    }
