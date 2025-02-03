package com.jux.familyspace.dtomapper;

import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

// Note: This class uses the default implementation of `getMemberDto` from the interface.

@Component
public class FamilyMemoryPictureDtoMapper implements FamilyMemberOneTypeDtoMapperInterface<FamilyMemoryPicture> {

    @Override
    public Class<FamilyMemoryPicture> getElementType() {
        return FamilyMemoryPicture.class;
    }
}