package com.jux.familyspace.service.elements_service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.ElementVisibility;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.HaikuRepository;
import com.jux.familyspace.service.family_service.FamilyService;
import com.jux.familyspace.service.spaces_service.PinBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HaikuService implements FamilyElementServiceInterface<Haiku> {

    private final HaikuRepository haikuRepository;
    private final ElementSizeTrackerInterface<Haiku> sizeTracker;
    private final AbstractElementAdder<Haiku> haikuAdder;
    private final FamilyService familyService;
    private final PinBoardService pinBoardService;
    private final FamilyMemberRepository familyMemberRepository;
    private Iterable<Haiku> haikus;


    @Override
    public Iterable<Haiku> getAllElements(String owner) {
        return haikuRepository.getByOwner(owner);
    }

    @Override
    public Haiku getElement(Long id) {
        return haikuRepository.findById(id).orElse(null);
    }


    @Override
    public Iterable<Haiku> getPublicElements() {
       return haikuRepository.getByVisibility(ElementVisibility.PUBLIC);
    }

    @Override
    public Iterable<Haiku> getSharedElements(String owner) {
        synchronizeSizeTracker();
        return haikuRepository.getByOwnerAndVisibility(owner,ElementVisibility.SHARED);
    }

    @Override
    public String makePublic(Long id, String owner) {

        try {
            Haiku haiku = haikuRepository.getByIdAndOwner(id, owner);
            haiku.setVisibility(ElementVisibility.PUBLIC);
            haikuRepository.save(haiku);
            return "haiku successfully made public";
        }catch (Exception e){
            return "error making public : " + e.getMessage();
        }

    }

    @Override
    public String makeShared(Long id, String owner) {

        try {
            Haiku haiku = haikuRepository.getByIdAndOwner(id, owner);
            haiku.setVisibility(ElementVisibility.SHARED);
            haikuRepository.save(haiku);
            return "haiku successfully shared";
        }catch (Exception e){
            return "error making shared : " + e.getMessage();
        }
   }

   public String markAsPinned(Long id, String owner) {

        try {
            Haiku haiku = haikuRepository.getByIdAndOwner(id, owner);
            System.out.println(haiku);
            haiku.setPinned(true);
            haiku.setVisibility(ElementVisibility.SHARED);
            haikuRepository.save(haiku);
            Long familyId = familyMemberRepository.getByUsername(owner).get().getFamilyId();
            System.out.println("family ID found : " + familyId);
            String output = pinBoardService.updatePinBoard(familyId, haiku);
            return "haiku successfully pinned -- "
                    + output;
        }catch (Exception e){
            return "error making pinned : " + e.getMessage();
        }
   }

   public String unpin(Long id, String owner) {

       try {
           Haiku haiku = haikuRepository.getByIdAndOwner(id, owner);
           haiku.setPinned(false);
           haikuRepository.save(haiku);
           Long familyId = familyMemberRepository.getByUsername(owner).get().getFamilyId();
           return "Picture successfully unpinned -- "
                   + pinBoardService.updatePinBoard(familyId, haiku);
       }catch (Exception e){
           return "error while unpinning : " + e.getMessage();
       }
   }


    @Override
    public Iterable<Haiku> getAllElementsByDate(Date date) {
    synchronizeSizeTracker();
        List<Haiku> haikuList = new ArrayList<>();
        if (this.haikus != null) {
            this.haikus.iterator().forEachRemaining(haiku -> {
                if (haiku.getDate().equals(date)) {
                    haikuList.add(haiku);
                }
            });
        }
        return haikus == null ? haikuRepository.getHaikusByDate(date) : haikuList;
    }


    @Override
    public String addElement(Haiku element) {
        try {
            String output = haikuAdder.addElement(element);
            sizeTracker.incrementSize(1);
            return output;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String removeElement(Long id, String owner) {

        try {
            haikuRepository.deleteByIdAndOwner(id, owner);
            sizeTracker.decrementSize(1);
            return "Haiku Deleted";
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    private void synchronizeSizeTracker() {
        System.out.println("tracker called");
        long actualSize = haikuRepository.count();
        if (actualSize != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(actualSize);
            haikus = haikuRepository.findAll();
        }
    }
}
