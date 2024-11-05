package com.jux.familyspace.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "element_type", discriminatorType = DiscriminatorType.STRING)
@Data
public class FamilyMemberElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FamilyElementType familyElementType;


}
