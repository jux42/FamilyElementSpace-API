package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyMemoryPictureProxy {

    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;

    public String addPicture(MultipartFile file, Date date, String owner) {
        try {
            FamilyMemoryPicture familyMemoryPicture = FamilyMemoryPicture.builder()
                    .picture(file.getBytes())
                    .build();
            if (date != null) {
                familyMemoryPicture.setDate(date);
            }
            familyMemoryPicture.setOwner(owner);
            return familyMemoryPictureService.addElement(familyMemoryPicture);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error while creating object : " + e.getMessage();
        }
    }

    public List<byte[]> getPicturesOnly(String owner) {
        ArrayList<byte[]> memoryPicsList = new ArrayList<>();
        familyMemoryPictureService.getAllElements(owner)
                .forEach(familyMemoryPicture ->
                        memoryPicsList.add(familyMemoryPicture.getPicture()));
        return memoryPicsList;
    }
}
