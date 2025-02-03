package com.jux.familyspace.dtomapper;

import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;
import com.jux.familyspace.model.DailyThought;
import org.springframework.stereotype.Component;


// Note: This class uses the default implementation of `getOneTypeMemberDto` from the interface.

@Component
public class DailyThoughtDtoMapper implements FamilyMemberOneTypeDtoMapperInterface<DailyThought> {
    @Override
    public Class<DailyThought> getElementType() {
        return DailyThought.class;
    }
}
