package com.jux.familyspace.model.family;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class FamilyGuest extends FamilyUser {

    public FamilyGuest() {
        super.setRole("GUEST");
    }
}
