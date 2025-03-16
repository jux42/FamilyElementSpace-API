package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.Haiku;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HaikuProxy {
    private final FamilyElementServiceInterface<Haiku> haikuService;


    public String addHaiku(String line1,
                           String line2,
                           String line3,
                           String owner) {
        Haiku haiku = Haiku.builder()
                .line1(line1)
                .line2(line2)
                .line3(line3)
                .build();
        haiku.setOwner(owner);

        return haikuService.addElement(haiku);

    }
}