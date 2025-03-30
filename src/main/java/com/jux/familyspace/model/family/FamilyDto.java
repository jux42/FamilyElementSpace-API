package com.jux.familyspace.model.family;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FamilyDto {
    private Long id;
    private String familyName;
    private List<String> membersNames;
}
