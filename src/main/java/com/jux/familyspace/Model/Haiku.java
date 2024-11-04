package com.jux.familyspace.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Haiku extends FamilyMemberElement {

    private String line1;
    private String line2;
    private String line3;

    @Enumerated
    private FamilyElementType familyElementType = FamilyElementType.HAIKU;
}
