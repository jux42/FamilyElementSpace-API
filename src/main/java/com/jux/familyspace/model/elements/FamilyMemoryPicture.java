package com.jux.familyspace.model.elements;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
public class FamilyMemoryPicture extends FamilyMemberElement {

    @Lob
    private byte[] picture;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FamilyElementType familyElementType = FamilyElementType.MEMORY_PIC;

    public FamilyMemoryPicture() {
        super.setCreationDate(new Date());

    }
}
