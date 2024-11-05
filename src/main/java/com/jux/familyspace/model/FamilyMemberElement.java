package com.jux.familyspace.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FamilyMemberElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private FamilyElementType familyElementType;



}
