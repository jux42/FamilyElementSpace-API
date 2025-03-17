package com.jux.familyspace.model.spaces;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;

@Data
@Entity
public class BuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private HashMap<String, String> items;

}
