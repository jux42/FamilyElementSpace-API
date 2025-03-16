package com.jux.familyspace.model.dtomapper;

import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;
import com.jux.familyspace.model.elements.Haiku;
import org.springframework.stereotype.Component;

// Note: This class uses the default implementation of `getMemberDto` from the interface.


@Component
public class HaikuElementDtoMapper implements FamilyMemberOneTypeDtoMapperInterface<Haiku> {
    @Override
    public Class<Haiku> getElementType() {
        return Haiku.class;
    }
}
