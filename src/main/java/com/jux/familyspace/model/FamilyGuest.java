package com.jux.familyspace.model;


import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class FamilyGuest extends FamilyUser {

    public FamilyGuest() {
        super.setRole("GUEST");
    }
}
