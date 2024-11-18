package com.jux.familyspace.dtomapper;

import com.jux.familyspace.api.FamilyMemberDtoMapperInterface;
import com.jux.familyspace.model.DailyThought;
import org.springframework.stereotype.Component;


// Note: This class uses the default implementation of `getMemberDto` from the interface.

@Component
public class DailyThoughtDtoMapper implements FamilyMemberDtoMapperInterface<DailyThought> {
    @Override
    public Class<DailyThought> getElementType() {
        return DailyThought.class;
    }
}
