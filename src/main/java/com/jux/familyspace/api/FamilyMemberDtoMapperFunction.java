package com.jux.familyspace.api;

import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.family.FamilyMemberDto;

@FunctionalInterface
public interface FamilyMemberDtoMapperFunction {

    FamilyMemberDto getMemberDto(FamilyMember familyMember);

}
