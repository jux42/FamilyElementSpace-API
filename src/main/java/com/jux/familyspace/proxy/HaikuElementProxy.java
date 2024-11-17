package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyMemberElementProxyInterface;
import com.jux.familyspace.model.Haiku;
import org.springframework.stereotype.Component;


@Component
public class HaikuElementProxy implements FamilyMemberElementProxyInterface<Haiku> {
    @Override
    public Class<Haiku> getElementType() {
        return Haiku.class;
    }
}
