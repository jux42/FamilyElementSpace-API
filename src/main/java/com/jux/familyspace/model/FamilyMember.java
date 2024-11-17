package com.jux.familyspace.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FamilyMemberElement> elements;

    @Transient
    private OnlineState onlineState;
}
