package com.jux.familyspace.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class FamilyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;


}
