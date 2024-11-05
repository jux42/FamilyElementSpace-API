package com.jux.familyspace.facade;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import com.jux.familyspace.service.FamilyMemoryPictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyMemoryPictureFacade {

    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;

    public String addPicture(MultipartFile file) {
        try{
            FamilyMemoryPicture familyMemoryPicture = FamilyMemoryPicture.builder()
                    .picture(file.getBytes())
                    .build();
            return familyMemoryPictureService.addElement(familyMemoryPicture);
        }catch (Exception e){
            log.error(e.getMessage());
            return "error while creating object : " + e.getMessage();
        }
    }

    public List<byte[]> getPicturesOnly() {
        ArrayList<byte[]> memoryPicsList = new ArrayList<>();
        familyMemoryPictureService.getAllElements()
                .forEach(familyMemoryPicture ->
                        memoryPicsList.add(familyMemoryPicture.getPicture()));
        return memoryPicsList;
    }
}
