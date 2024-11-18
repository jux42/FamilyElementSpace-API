package com.jux.familyspace.controller;

import com.jux.familyspace.dtomappers.DailyThoughtDtoMapper;
import com.jux.familyspace.model.*;
import com.jux.familyspace.service.DailyThoughtService;
import com.jux.familyspace.service.FamilyMemoryPictureService;
import com.jux.familyspace.service.HaikuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public")
@RestController
@RequiredArgsConstructor
public class PublicSpaceController {

    private final DailyThoughtDtoMapper dailyThoughtDtoMapper;
    private final DailyThoughtService dailyThoughtService;
    private final FamilyMemoryPictureService familyMemoryPictureService;
    private final HaikuService haikuService;


    @GetMapping("/dailys")
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
