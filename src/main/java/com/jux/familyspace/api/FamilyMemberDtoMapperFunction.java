package com.jux.familyspace.api;

import com.jux.familyspace.model.users.FamilyMember;
import com.jux.familyspace.model.users.FamilyMemberDto;

@FunctionalInterface
public interface FamilyMemberDtoMapperFunction {

    FamilyMemberDto getMemberDto(FamilyMember familyMember);

}
