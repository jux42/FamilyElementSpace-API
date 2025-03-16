package com.jux.familyspace.model.dtomapper;

import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;
import com.jux.familyspace.model.elements.DailyThought;
import org.springframework.stereotype.Component;


// Note: This class uses the default implementation of `getOneTypeMemberDto` from the interface.

@Component
public class DailyThoughtDtoMapper implements FamilyMemberOneTypeDtoMapperInterface<DailyThought> {
    @Override
    public Class<DailyThought> getElementType() {
        return DailyThought.class;
    }
}
