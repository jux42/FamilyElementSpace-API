package com.jux.familyspace.dtomappers;

import com.jux.familyspace.api.FamilyMemberDtoMapperInterface;
import com.jux.familyspace.model.Haiku;
import org.springframework.stereotype.Component;

// Note: This class uses the default implementation of `getMemberDto` from the interface.


@Component
public class HaikuElementDtoMapper implements FamilyMemberDtoMapperInterface<Haiku> {
    @Override
    public Class<Haiku> getElementType() {
        return Haiku.class;
    }
}
