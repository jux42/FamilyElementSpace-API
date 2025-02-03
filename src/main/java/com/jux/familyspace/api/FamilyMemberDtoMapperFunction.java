package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.model.FamilyMemberDto;

@FunctionalInterface
public interface FamilyMemberDtoMapperFunction {

    FamilyMemberDto getMemberDto(FamilyMember familyMember);

}
