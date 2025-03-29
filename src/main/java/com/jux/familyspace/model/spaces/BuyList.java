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

    private Long familyId;

    @OneToOne(mappedBy = "buyList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PinBoard pinBoard;


    @ElementCollection
    private HashMap<Long, String> items;

    public void addItem(Long userID, String itemDescription){
        items.put(userID, itemDescription);
    }
}
