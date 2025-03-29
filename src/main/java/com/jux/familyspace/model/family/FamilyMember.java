package com.jux.familyspace.model.family;

import com.jux.familyspace.model.elements.FamilyMemberElement;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @Nullable
    private Long familyId;

    private String tagline;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FamilyMemberElement> elements = new ArrayList<>();

    @Transient
    private OnlineState onlineState;

    public void addElements(FamilyMemberElement...familyMemberElement) {
        Collections.addAll(elements, familyMemberElement);
    }

}
