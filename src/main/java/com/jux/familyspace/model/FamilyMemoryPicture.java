package com.jux.familyspace.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
public class FamilyMemoryPicture extends FamilyMemberElement{

    @Lob
    private byte[] picture;

    @Builder.Default
    private FamilyElementType familyElementType = FamilyElementType.MEMORY_PIC;

    public FamilyMemoryPicture(){
        this.setFamilyElementType(FamilyElementType.MEMORY_PIC);
    }
}
