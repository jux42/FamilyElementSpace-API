package com.jux.familyspace.model.elements;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor

public class Haiku extends FamilyMemberElement {

    private String line1;
    private String line2;
    private String line3;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FamilyElementType familyElementType = FamilyElementType.HAIKU;

    public Haiku() {
        super.setCreationDate(new Date());

    }
}
