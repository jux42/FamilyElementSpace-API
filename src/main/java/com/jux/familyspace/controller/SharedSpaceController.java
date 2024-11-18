
package com.jux.familyspace.controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.dtomappers.DailyThoughtDtoMapper;
import com.jux.familyspace.model.*;
        import com.jux.familyspace.service.DailyThoughtService;
import com.jux.familyspace.service.FamilyMemoryPictureService;
import com.jux.familyspace.service.HaikuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/shared")
@RestController
@RequiredArgsConstructor
public class SharedSpaceController {

    private final FamilyElementServiceInterface<DailyThought> dailyThoughtService;
    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;
    private final FamilyElementServiceInterface<Haiku> haikuService;


    @GetMapping("/dailys/{owner}")
    public ResponseEntity<Iterable<DailyThought>> getSharedDailys(@PathVariable String owner){

        return ResponseEntity.ok(dailyThoughtService.getSharedElements(owner));
    }

    @GetMapping("/memorypics/{owner}")
    public ResponseEntity<Iterable<FamilyMemoryPicture>> getSharedMemoryPictures(@PathVariable String owner){

        return ResponseEntity.ok(familyMemoryPictureService.getSharedElements(owner));
    }

    @GetMapping("/haikus/{owner}")
    public ResponseEntity<Iterable<Haiku>> getSharedHaikus(@PathVariable String owner){

        return ResponseEntity.ok(haikuService.getSharedElements(owner));
    }




}
