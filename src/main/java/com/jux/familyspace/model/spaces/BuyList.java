package com.jux.familyspace.model.spaces;


import com.jux.familyspace.controller.spaces_controller.ItemToBuy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long familyId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ItemToBuy> items = new ArrayList<>();

    public void addItem(Long userID, String itemDescription){
        ItemToBuy itemToBuy = ItemToBuy.builder()
                .userId(userID)
                .description(itemDescription)
                .build();

        items.add(itemToBuy);
    }

    public void remove(Long id){
        items.remove(id);
    }
}
