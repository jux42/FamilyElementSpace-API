package com.jux.familyspace.model.family;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FamilyDto {
    Long id;
    String familyName;
    List<String> membersNames;
}
