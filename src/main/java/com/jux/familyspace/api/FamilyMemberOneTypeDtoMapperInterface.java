package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.model.FamilyMemberElement;
import com.jux.familyspace.model.FamilyMemberOneTypeDto;

import java.util.List;
import java.util.stream.Collectors;

public interface FamilyMemberOneTypeDtoMapperInterface<T extends FamilyMemberElement> {

    Class<T> getElementType();

    default FamilyMemberOneTypeDto getOneTypeMemberDto(FamilyMember member) {
        List<FamilyMemberElement> filteredElements = member.getElements().stream()
                .filter(element -> getElementType().isInstance(element))
                .map(element -> getElementType().cast(element))
                .collect(Collectors.toList());

        return FamilyMemberOneTypeDto.builder()
                .id(member.getId())
                .name(member.getUsername())
                .elements(filteredElements)
                .build();
    }


}
