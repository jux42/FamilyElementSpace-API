package com.jux.familyspace.model.spaces;

import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemberElement;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
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

    private Long familyId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private BuyList buyList = new BuyList();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PostIt> postIts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Haiku> pinnedHaikus = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DailyThought> pinnedDailyThoughts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FamilyMemoryPicture> pinnedFamilyPictures = new ArrayList<>();

    public void addPostIt(PostIt postIt) {
        this.postIts.add(postIt);
    }

    public void addPinnedElement(Haiku element) {
            pinnedHaikus.add(element);
        }

    public void addPinnedElement(DailyThought element) {
        pinnedDailyThoughts.add(element);
    }

    public void addPinnedElement(FamilyMemoryPicture element) {
        pinnedFamilyPictures.add(element);
    }



    public void removePinnedElement(Haiku element) {
            pinnedHaikus.remove(element);
        }

    public void removePinnedElement(DailyThought element) {
        pinnedDailyThoughts.remove(element);
    }

    public void removePinnedElement(FamilyMemoryPicture element) {
        pinnedFamilyPictures.remove(element);
    }

}
