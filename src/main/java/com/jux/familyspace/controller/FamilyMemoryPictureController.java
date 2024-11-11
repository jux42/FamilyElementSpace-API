package com.jux.familyspace.controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.facade.FamilyMemoryPictureFacade;
import com.jux.familyspace.model.FamilyMemoryPicture;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class FamilyMemoryPictureController {

    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;
    private final FamilyMemoryPictureFacade familyMemoryPictureFacade;


    @GetMapping("memorypics")
    public ResponseEntity<Iterable<FamilyMemoryPicture>> getMemoryPictures() {
        return ResponseEntity.ok(familyMemoryPictureService.getAllElements());
    }

    @GetMapping("memorypicsonly")
    public ResponseEntity<Iterable<byte[]>> getMemoryPicturesOnly() {
        return ResponseEntity.ok(familyMemoryPictureFacade.getPicturesOnly());
    }

    @GetMapping("memorypic/{id}")
    public ResponseEntity<FamilyMemoryPicture> getMemoryPicture(@PathVariable Long id) {
        return ResponseEntity.ok(familyMemoryPictureService.getElement(id));
    }

    @GetMapping("memorypiconly/{id}")
    public ResponseEntity<byte[]> getMemoryPictureOnly(@PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(familyMemoryPictureService.getElement(id).getPicture());
    }

    @PostMapping("memorypic")
    public ResponseEntity<String> addMemoryPicture(@RequestBody MultipartFile file,
                                                   @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        return ResponseEntity.ok(familyMemoryPictureFacade.addPicture(file, date));
    }

    @DeleteMapping("memorypic/{id}")
    public ResponseEntity<String> deleteMemoryPicture(@PathVariable Long id) {
        return ResponseEntity.ok(familyMemoryPictureService.removeElement(id));
    }
}
