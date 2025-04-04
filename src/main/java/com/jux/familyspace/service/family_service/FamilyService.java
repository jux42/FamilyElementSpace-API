package com.jux.familyspace.service.family_service;

import com.jux.familyspace.model.dtomapper.FamilyDtoMapper;
import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyDto;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyDtoMapper familyDtoMapper;

    public FamilyDto getFamilyDetailsDto(String familyName) {

        Family family =  familyRepository.findByFamilyName(familyName).orElseThrow(NoSuchElementException::new);
        return familyDtoMapper.mapDto(family);
    }

    public String createFamily(String username, String familyName, String secret) {

        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(username);
        if (familyMember.isEmpty()) {
            return "you are not a registered user. Please register first";
        }

        if (familyRepository.findByFamilyName(familyName).isPresent()) {
            int n = 1;
            while (familyRepository.findByFamilyName(familyName + n).isPresent()) {
                n++;
            }
            return "this family name already exists !\n" +
                    "suggested alternative : " + familyName + n;
        }

        Family family = Family.builder()
                .familyName(familyName)
                .secret(secret)
                .build();
        family.setPinBoard(PinBoard.builder()
                .familyId(family.getId())
                .build());
        family.getPinBoard()
                .getBuyList()
                .setFamilyId(family.getId());

        family.addFamilyMember(familyMember.get());
        familyRepository.save(family);
        familyMember.get().setFamilyId(family.getId());
        familyMemberRepository.save(familyMember.get());
        return "Family sucessfully created";

    }

    public String joinFamily(String username, String familyName, String secret) {

        FamilyMember familyMember = familyMemberRepository
                .getByUsername(username)
                .orElseThrow(RuntimeException::new);

        Family family = familyRepository
                .findByFamilyName(familyName)
                .orElseThrow(RuntimeException::new);

        if (secret.equals(family.getSecret()))

        {
            family.addFamilyMember(familyMember);
            familyMember.setFamilyId(family.getId());
            familyRepository.save(family);
            familyMemberRepository.save(familyMember);
            return "welcome to the " +familyName+ " family !";
        } else {
            return "Sorry, your secret does not match the family secret";
        }

    }

    public PinBoard getPinBoard(Long familyId) {
        return familyRepository.findById(familyId).isEmpty()
                ? null
                : familyRepository.findById(familyId).get().getPinBoard();
    }

}
