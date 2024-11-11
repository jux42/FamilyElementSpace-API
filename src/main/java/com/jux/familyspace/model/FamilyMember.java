package com.jux.familyspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMember extends FamilyUser {

    @Lob
    private byte[] avatar;

    private String tagline;

    @Transient
    private OnlineState onlineState;
}
