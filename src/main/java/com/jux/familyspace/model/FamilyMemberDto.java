package com.jux.familyspace.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FamilyMemberDto {

    private Long id;
    private String name;
    private byte[] avatar;
    private String tagline;
    private List<Haiku> haikus;
    private List<DailyThought> dailyThoughts;
    private List <FamilyMemoryPicture> familyMemoryPictures;

}
