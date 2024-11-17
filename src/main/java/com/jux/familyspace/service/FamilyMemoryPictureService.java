package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.ElementAdder;
import com.jux.familyspace.component.FamilyMemoryPictureSizeTracker;
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
    private final ElementAdder elementAdder;

    private List<FamilyMemoryPicture> pictures;

    @Override
    public Iterable<FamilyMemoryPicture> getAllElements() {
        if (pictures == null || pictures.spliterator().getExactSizeIfKnown() != sizeTracker.getTotalSize()) {
            pictures = familyMemoryPictureRepository.findAll();

        }
        return pictures;
    }

    @Override
    public Iterable<FamilyMemoryPicture> getAllElementsByDate(Date date) {
        if (pictures == null || pictures.spliterator().getExactSizeIfKnown() != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(familyMemoryPictureRepository.findAll().size());
            pictures = familyMemoryPictureRepository.findAll();
        }
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
            String output = elementAdder.addElement(element);
            sizeTracker.incrementSize(1);
            return output;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String removeElement(Long id) {
        return "";
    }
}

