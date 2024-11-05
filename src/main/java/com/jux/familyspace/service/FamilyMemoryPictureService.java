package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyMemoryPictureService implements FamilyElementServiceInterface<FamilyMemoryPicture> {

    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;

    @Override
    public Iterable<FamilyMemoryPicture> getAllElements() {
        return familyMemoryPictureRepository.findAll();
    }

    @Override
    public FamilyMemoryPicture getElement(Long id) {
        return familyMemoryPictureRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(FamilyMemoryPicture element) {
        try{
            familyMemoryPictureRepository.save(element);
            return "memoryPic element saved";
        }catch (Exception e){
            log.error(e.getMessage());
            return "error while saving element : " + e.getMessage();
        }

    }

    @Override
    public String removeElement(Long id) {
        return "";
    }
}
