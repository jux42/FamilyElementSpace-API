package com.jux.familyspace.dtomappers;

import com.jux.familyspace.api.FamilyMemberElementDtoMapperInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

// Note: This class uses the default implementation of `getMemberDto` from the interface.

@Component
public class FamilyMemoryPictureDtoMapper implements FamilyMemberElementDtoMapperInterface<FamilyMemoryPicture> {

    @Override
    public Class<FamilyMemoryPicture> getElementType() {
        return FamilyMemoryPicture.class;
    }
}