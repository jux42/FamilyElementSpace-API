package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.FamilyMemoryPictureSizeTracker;
import com.jux.familyspace.model.ElementVisibility;
import com.jux.familyspace.model.FamilyMemoryPicture;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyMemoryPictureService implements FamilyElementServiceInterface<FamilyMemoryPicture> {

    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;
    private final FamilyMemoryPictureSizeTracker sizeTracker;
    private final FamilyMemoryPictureAdder familyMemoryPictureAdder;

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
    public Iterable<FamilyMemoryPicture> getAllElementsByDate(Date date) {
        synchronizeSizeTracker();
        List<FamilyMemoryPicture> pictureList = new ArrayList<>();
        pictures.iterator().forEachRemaining(memoryPicture -> {
            if (memoryPicture.getDate().equals(date)) {
                pictureList.add(memoryPicture);
            }
        });
        return pictureList;
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

    public void synchronizeSizeTracker() {
        int actualSize = (int) familyMemoryPictureRepository.count();
        if (actualSize != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(actualSize);
            pictures = familyMemoryPictureRepository.findAll();
        }
    }

}



