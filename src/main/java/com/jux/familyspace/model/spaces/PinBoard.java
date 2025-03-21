package com.jux.familyspace.model.spaces;

import com.jux.familyspace.model.family.Family;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinBoard {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Family family;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BuyList buyList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PostIt> postIts = new ArrayList<>();


}
