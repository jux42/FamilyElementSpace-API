package com.jux.familyspace.model.family;


import com.jux.familyspace.model.spaces.PinBoard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String familyName;

    String secret;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    List<FamilyMember> members = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    PinBoard pinBoard;


    public void addFamilyMember(FamilyMember familyMember) {
        this.members.add(familyMember);
    }

    public List<String> getMemberNames() {

        List<String> membersNames = new ArrayList<>();
        this.members.forEach((member) -> membersNames.add(member.getUsername()));
        return membersNames;
    }
}
