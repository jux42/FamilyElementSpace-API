package com.jux.familyspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class FamilyMember extends User {

    @Lob
    private byte[] avatar;

    private String tagline;

    @Transient
    private OnlineState onlineState;
}
