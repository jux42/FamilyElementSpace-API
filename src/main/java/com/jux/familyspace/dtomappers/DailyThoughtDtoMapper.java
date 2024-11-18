package com.jux.familyspace.dtomappers;

import com.jux.familyspace.api.FamilyMemberElementDtoMapperInterface;
import com.jux.familyspace.model.DailyThought;
import org.springframework.stereotype.Component;


// Note: This class uses the default implementation of `getMemberDto` from the interface.

@Component
public class DailyThoughtDtoMapper implements FamilyMemberElementDtoMapperInterface<DailyThought> {
    @Override
    public Class<DailyThought> getElementType() {
        return DailyThought.class;
    }
}
