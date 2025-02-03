package com.jux.familyspace.controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.model.FamilyMemoryPicture;
import com.jux.familyspace.model.Haiku;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public")
@RestController
@RequiredArgsConstructor
public class PublicSpaceController {

    private final FamilyElementServiceInterface<DailyThought> dailyThoughtService;
    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;
    private final FamilyElementServiceInterface<Haiku> haikuService;


    @GetMapping("/thoughts")
    public ResponseEntity<Iterable<DailyThought>> getPublicDailys(){

        return ResponseEntity.ok(dailyThoughtService.getPublicElements());
    }

    @GetMapping("/memorypics")
    public ResponseEntity<Iterable<FamilyMemoryPicture>> getPublicPics(){

        return ResponseEntity.ok(familyMemoryPictureService.getPublicElements());
    }

    @GetMapping("/haikus")
    public ResponseEntity<Iterable<Haiku>> getPublicHaikus(){

        return ResponseEntity.ok(haikuService.getPublicElements());
    }






}
