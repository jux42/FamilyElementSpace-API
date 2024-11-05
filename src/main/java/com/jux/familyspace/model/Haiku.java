package com.jux.familyspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor

public class Haiku extends FamilyMemberElement {

    private String line1;
    private String line2;
    private String line3;

    @Enumerated
    private FamilyElementType familyElementType;

    public Haiku() {
        this.familyElementType = FamilyElementType.HAIKU;
    }
}
