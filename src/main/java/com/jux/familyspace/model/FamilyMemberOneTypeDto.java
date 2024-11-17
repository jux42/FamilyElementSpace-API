package com.jux.familyspace.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FamilyMemberOneTypeDto {

    private Long id;
    private String name;
    private List<FamilyMemberElement> elements;

}
