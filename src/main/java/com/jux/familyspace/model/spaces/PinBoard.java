package com.jux.familyspace.model.spaces;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PinBoard {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String familyID;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BuyList buyList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PostIt> postIts = new ArrayList<>();


}
