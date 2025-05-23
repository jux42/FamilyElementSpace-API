package com.jux.familyspace.service.elements_service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.ElementVisibility;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import com.jux.familyspace.service.family_service.FamilyService;
import com.jux.familyspace.service.spaces_service.PinBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyMemoryPictureService implements FamilyElementServiceInterface<FamilyMemoryPicture> {

    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;
    private final ElementSizeTrackerInterface<FamilyMemoryPicture> sizeTracker;
    private final AbstractElementAdder<FamilyMemoryPicture> familyMemoryPictureAdder;
    private final PinBoardService pinBoardService;
    private final FamilyService familyService;
    private final FamilyMemberRepository familyMemberRepository;
    private List<FamilyMemoryPicture> pictures;


    @Override
    public Iterable<FamilyMemoryPicture> getAllElements(String owner) {
        return familyMemoryPictureRepository.getByOwner(owner);
    }

    @Override
    public Iterable<FamilyMemoryPicture> getPublicElements() {
        synchronizeSizeTracker();
       return familyMemoryPictureRepository.getByVisibility(ElementVisibility.PUBLIC);
    }

    @Override
    public Iterable<FamilyMemoryPicture> getSharedElements(String owner) {
        synchronizeSizeTracker();
       return familyMemoryPictureRepository.getByOwnerAndVisibility(owner, ElementVisibility.SHARED);
    }

    @Override
    public String makePublic(Long id, String owner) {
        try{
            FamilyMemoryPicture memoryPic = familyMemoryPictureRepository.getByIdAndOwner(id, owner);
            memoryPic.setVisibility(ElementVisibility.PUBLIC);
            familyMemoryPictureRepository.save(memoryPic);
            return "memoryPic successfully made public";
        }catch (Exception e){
            return "error making public picture : " + e.getMessage();
        }

    }

    @Override
    public String makeShared(Long id, String owner) {

        try {
            FamilyMemoryPicture memoryPic = familyMemoryPictureRepository.getByIdAndOwner(id, owner);
            memoryPic.setVisibility(ElementVisibility.SHARED);
            familyMemoryPictureRepository.save(memoryPic);
            return "memoryPic successfully shared";
        }catch (Exception e){
            return "error making shared picture : " + e.getMessage();
        }
    }

    public String markAsPinned(Long id, String owner) {

        try {
            FamilyMemoryPicture memoryPicture = familyMemoryPictureRepository.getByIdAndOwner(id, owner);
            memoryPicture.setPinned(true);
            familyMemoryPictureRepository.save(memoryPicture);
            Long familyId = familyMemberRepository.getByUsername(owner).get().getFamilyId();
            return ("Picture successfully pinned -- "
                    + pinBoardService.updatePinBoard(familyId, memoryPicture));
        }catch (Exception e){
            return "error making pinned : " + e.getMessage();
        }
    }

    public String unpin(Long id, String owner) {

        try {
            FamilyMemoryPicture memoryPicture = familyMemoryPictureRepository.getByIdAndOwner(id, owner);
            memoryPicture.setPinned(false);
            memoryPicture.setVisibility(ElementVisibility.SHARED);
            familyMemoryPictureRepository.save(memoryPicture);
            Long familyId = familyMemberRepository.getByUsername(owner).get().getFamilyId();
            return ("Picture successfully unpinned -- "
                    + pinBoardService.updatePinBoard(familyId, memoryPicture));

        }catch (Exception e){
            return "error while unpinning : " + e.getMessage();
        }
    }

    @Override
    public Iterable<FamilyMemoryPicture> getAllElementsByDate(Date date) {
        return familyMemoryPictureRepository.getFamilyMemoryPicturesByDate(date);
    }

    @Override
    public FamilyMemoryPicture getElement(Long id) {
        return familyMemoryPictureRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(FamilyMemoryPicture element) {
        try {
            String output = familyMemoryPictureAdder.addElement(element);
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
            familyMemoryPictureRepository.deleteByIdAndOwner(id, owner);
            sizeTracker.decrementSize(1);
            return "Picture Deleted";
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    private void synchronizeSizeTracker() {
        System.out.println("tracker called");
        long actualSize = familyMemoryPictureRepository.count();
        if (actualSize != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(actualSize);
            pictures = familyMemoryPictureRepository.findAll();
        }
    }

}



