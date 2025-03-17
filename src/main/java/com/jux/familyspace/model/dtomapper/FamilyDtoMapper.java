package com.jux.familyspace.model.dtomapper;

import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyDto;
import org.springframework.stereotype.Component;

@Component
public class FamilyDtoMapper {

    public FamilyDto mapDto(Family family) {
        return FamilyDto.builder()
                .familyName(family.getFamilyName())
                .membersNames(family.getMemberNames())
                .build();
    }

}
