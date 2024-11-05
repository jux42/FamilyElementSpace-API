package com.jux.familyspace.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
public class FamilyMemoryPicture extends FamilyMemberElement{

    FamilyElementType familyElementType;

    @Lob
    private byte[] picture;

    public FamilyMemoryPicture(){
        this.familyElementType = FamilyElementType.MEMORY_PIC;
    }
}
