package com.jux.familyspace.controller.elements_controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.proxy.FamilyMemoryPictureProxy;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class FamilyMemoryPictureController {

    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;
    private final FamilyMemoryPictureProxy familyMemoryPictureProxy;


    @GetMapping("memorypics")
    public ResponseEntity<Iterable<FamilyMemoryPicture>> getMemoryPictures(Principal principal) {
        return ResponseEntity.ok(familyMemoryPictureService.getAllElements(principal.getName()));
    }

    @GetMapping("memorypicsonly")
    public ResponseEntity<Iterable<byte[]>> getMemoryPicturesOnly(Principal principal) {
        return ResponseEntity.ok(familyMemoryPictureProxy.getPicturesOnly(principal.getName()));
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
                                                   @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                   Principal principal) {

        return ResponseEntity.ok(familyMemoryPictureProxy.addPicture(file, date, principal.getName()));
    }

    @DeleteMapping("memorypic/{id}")
    public ResponseEntity<String> deleteMemoryPicture(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(familyMemoryPictureService.removeElement(id, principal.getName()));
    }
}
