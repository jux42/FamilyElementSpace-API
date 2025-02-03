package com.jux.familyspace.service;


import com.jux.familyspace.api.FamilyMemberDtoMapperFunction;
import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;
import com.jux.familyspace.model.*;
import com.jux.familyspace.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyMemberOneTypeDtoMapperInterface<Haiku> haikuDtoMapper;
    private final FamilyMemberOneTypeDtoMapperInterface<DailyThought> dailyDtoMapper;
    private final FamilyMemberOneTypeDtoMapperInterface<FamilyMemoryPicture> memoryPicsDtoMapper;


    public FamilyMember getCurrentUserByName(String username) {
        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);
        return familyMember.orElse(null);
    }

    public FamilyMemberDto getMemberDto(Principal principal) {
        FamilyMember familyMember = getCurrentUserByName(principal.getName());
        return familyMember != null ? memberDtoMapper.getMemberDto(familyMember) : null;
    }

    public FamilyMemberOneTypeDto getMemberHaikuDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return haikuDtoMapper.getOneTypeMemberDto(familyMember);
    }

    public FamilyMemberOneTypeDto getMemberDailyThoughtsDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return dailyDtoMapper.getOneTypeMemberDto(familyMember);
    }

    public FamilyMemberOneTypeDto getMemberMemoryPicsDto(Principal principal) {

        FamilyMember familyMember = getCurrentUserByName(principal.getName());

        return memoryPicsDtoMapper.getOneTypeMemberDto(familyMember);
    }

    private final FamilyMemberDtoMapperFunction memberDtoMapper = (FamilyMember member) -> FamilyMemberDto.builder()
            .id(member.getId())
            .name(member.getUsername())
            .avatar(member.getAvatar())
            .tagline(member.getTagline())
            .haikus(member.getElements().stream()
                    .filter(e -> e instanceof Haiku)
                    .map(e -> (Haiku) e)
                    .collect(Collectors.toList()))
            .dailyThoughts(member.getElements().stream()
                    .filter(e -> e instanceof DailyThought)
                    .map(e -> (DailyThought) e)
                    .collect(Collectors.toList()))
            .familyMemoryPictures(member.getElements().stream()
                    .filter(e -> e instanceof FamilyMemoryPicture)
                    .map(e -> (FamilyMemoryPicture) e)
                    .collect(Collectors.toList()))
            .build();



    }
