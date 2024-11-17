package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyMemberElementProxyInterface;
import com.jux.familyspace.model.DailyThought;
import org.hibernate.annotations.Proxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DailyThoughtProxy implements FamilyMemberElementProxyInterface<DailyThought> {
    @Override
    public Class<DailyThought> getElementType() {
        return DailyThought.class;
    }
}
