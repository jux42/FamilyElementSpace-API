package com.jux.familyspace.model.users;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class FamilyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyID;

    private String username;
    private String password;

    private String role;


}
