package com.jux.familyspace.service.family_service;


import com.jux.familyspace.api.FamilyMemberDtoMapperFunction;
import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;
import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemberOneTypeDto;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.family.FamilyMemberDto;
import com.jux.familyspace.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    private final FamilyMemberOneTypeDtoMapperInterface<Haiku> haikuElementDtoMapper;
    private final FamilyMemberOneTypeDtoMapperInterface<DailyThought> dailyThoughtDtoMapper;
    private final FamilyMemberOneTypeDtoMapperInterface<FamilyMemoryPicture> familyMemoryPictureDtoMapper;




    public FamilyMember getCurrentUserByName(String username) {
        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);
        return familyMember.orElse(null);
    }

    public FamilyMemberDto getMemberDto(Principal principal) {
        FamilyMember familyMember = getCurrentUserByName(principal.getName());
        return familyMember != null ? memberDtoMapper.getMemberDto(familyMember) : null;
    }

    public FamilyMemberOneTypeDto getMemberHaikuDto(String username) {
        System.out.println("Appel à getMemberHaikuDto avec username: " + username);

        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);
        System.out.println("Résultat de getByUsername: " + familyMember);

        if (familyMember.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé, retour d'un DTO vide.");
            return FamilyMemberOneTypeDto.builder().build();
        }
        System.out.println("Appel du mapper avec: " + familyMember);
        FamilyMemberOneTypeDto dto = haikuElementDtoMapper.getOneTypeMemberDto(familyMember.get());

        System.out.println("Résultat du mapping DTO: " + dto);

        return dto;
    }

    public FamilyMemberOneTypeDto getMemberDailyThoughtsDto(String username) {

        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);

        return familyMember.isPresent() ? dailyThoughtDtoMapper.getOneTypeMemberDto(familyMember.get())
                : FamilyMemberOneTypeDto.builder().build();
    }

    public FamilyMemberOneTypeDto getMemberMemoryPicsDto(String username) {

        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);

        return familyMember.isPresent() ? familyMemoryPictureDtoMapper.getOneTypeMemberDto(familyMember.get())
                : FamilyMemberOneTypeDto.builder().build();
    }

    private final FamilyMemberDtoMapperFunction memberDtoMapper = (FamilyMember member) -> FamilyMemberDto.builder()
            .id(member.getId())
            .name(member.getUsername())
            .familyID(member.getFamilyId())
            .avatar(member.getAvatar())
            .tagline(member.getTagline())
            .haikus(Optional.ofNullable(member.getElements()).orElse(Collections.emptyList()).stream()
                    .filter(e -> e instanceof Haiku)
                    .map(e -> (Haiku) e)
                    .collect(Collectors.toList()))
            .dailyThoughts(Optional.ofNullable(member.getElements()).orElse(Collections.emptyList()).stream()
                    .filter(e -> e instanceof DailyThought)
                    .map(e -> (DailyThought) e)
                    .collect(Collectors.toList()))
            .familyMemoryPictures(Optional.ofNullable(member.getElements()).orElse(Collections.emptyList()).stream()
                    .filter(e -> e instanceof FamilyMemoryPicture)
                    .map(e -> (FamilyMemoryPicture) e)
                    .collect(Collectors.toList()))
            .build();



    }
