package com.jux.familyspace.Service;

import com.jux.familyspace.Interface.FamilyElementServiceInterface;
import com.jux.familyspace.Model.FamilyElementType;
import com.jux.familyspace.Model.Haiku;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class HaikuProxyService {
    private final FamilyElementServiceInterface<Haiku> haikuService;



    public String addHaiku( String line1,
                            String line2,
                            String line3) {
        Haiku haiku = Haiku.builder()
                .familyElementType(FamilyElementType.HAIKU)
                .line1(line1)
                .line2(line2)
                .line3(line3)
                .build();

        return haikuService.addElement(haiku);

    }
}