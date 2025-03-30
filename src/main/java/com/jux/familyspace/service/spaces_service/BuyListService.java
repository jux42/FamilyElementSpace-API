package com.jux.familyspace.service.spaces_service;

import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.BuyList;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyListService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;


    public BuyList getBuyList(Long familyId) {

        Optional<Family> family = familyRepository.findById(familyId);
        return family.get().getPinBoard().getBuyList();
    }

    public String addItemToBuyList(Long userId, String item){

        Optional<FamilyMember> user = familyMemberRepository.findById(userId);
        if (user.isEmpty() || user.get().getFamilyId() == null) throw new RuntimeException("user not found or not part to a family");
        FamilyMember familyMember = user.get();
        Optional<Family> family = familyRepository.findById(Objects.requireNonNull(familyMember.getFamilyId()));
        if (family.isEmpty()) throw new RuntimeException("item could not be added to any known family buylist");

        family.ifPresent(f->{
            f.getPinBoard()
                    .getBuyList()
                    .addItem(userId, item);
            familyRepository.save(f);
        });
        return "item added to buy list";

    }

    public String removeItemFromBuyList(Long userId, Long itemId){
        Optional<FamilyMember> user = familyMemberRepository.findById(userId);
        if (user.isEmpty() || user.get().getFamilyId() == null) {
            throw new RuntimeException("you are not registered, or not a member of any family");
        }
        FamilyMember familyMember = user.get();
        Optional<Family> family = familyRepository.findById(familyMember.getFamilyId());
        if (family.isEmpty()) {
            throw new RuntimeException("item could not be added to any known family buylist");
        }

        family.get().getPinBoard().getBuyList().remove(itemId);
        familyRepository.save(family.get());
        return "item removed from buy list";
    }

}
