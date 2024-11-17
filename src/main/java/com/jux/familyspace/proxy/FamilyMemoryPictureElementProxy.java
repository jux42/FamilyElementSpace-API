package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyMemberElementProxyInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

@Component
public class FamilyMemoryPictureElementProxy implements FamilyMemberElementProxyInterface<FamilyMemoryPicture> {

    @Override
    public Class<FamilyMemoryPicture> getElementType() {
        return FamilyMemoryPicture.class;
    }
}