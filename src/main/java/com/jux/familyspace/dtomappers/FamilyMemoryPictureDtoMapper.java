package com.jux.familyspace.dtomappers;

import com.jux.familyspace.api.FamilyMemberDtoMapperInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

// Note: This class uses the default implementation of `getMemberDto` from the interface.

@Component
public class FamilyMemoryPictureDtoMapper implements FamilyMemberDtoMapperInterface<FamilyMemoryPicture> {

    @Override
    public Class<FamilyMemoryPicture> getElementType() {
        return FamilyMemoryPicture.class;
    }
}