package com.jux.familyspace.model.family;

import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Builder
@Data
public class FamilyMemberDto {

    private Long id;
    private String name;
    private Long familyID;
    private byte[] avatar;
    private String tagline;

    @Builder.Default
    private List<Haiku> haikus = Collections.emptyList();
    @Builder.Default
    private List<DailyThought> dailyThoughts = Collections.emptyList();
    @Builder.Default
    private List<FamilyMemoryPicture> familyMemoryPictures = Collections.emptyList();

}
