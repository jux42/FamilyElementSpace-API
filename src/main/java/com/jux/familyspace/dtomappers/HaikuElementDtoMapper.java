package com.jux.familyspace.dtomappers;

import com.jux.familyspace.api.FamilyMemberElementDtoMapperInterface;
import com.jux.familyspace.model.Haiku;
import org.springframework.stereotype.Component;

// Note: This class uses the default implementation of `getMemberDto` from the interface.


@Component
public class HaikuElementDtoMapper implements FamilyMemberElementDtoMapperInterface<Haiku> {
    @Override
    public Class<Haiku> getElementType() {
        return Haiku.class;
    }
}
