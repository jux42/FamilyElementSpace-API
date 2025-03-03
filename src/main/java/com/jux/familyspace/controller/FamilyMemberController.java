package com.jux.familyspace.controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.*;
import com.jux.familyspace.service.DailyThoughtService;
import com.jux.familyspace.service.FamilyMemberService;
import com.jux.familyspace.service.FamilyMemoryPictureService;
import com.jux.familyspace.service.HaikuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/user")
public class FamilyMemberController {


    private final FamilyMemberService familyMemberService;
    private final FamilyElementServiceInterface<Haiku> haikuService;
    private final FamilyElementServiceInterface<DailyThought> dailyThoughtService;
    private final FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;

    public FamilyMemberController(FamilyMemberService familyMemberService, HaikuService haikuService, DailyThoughtService dailyThoughtService, FamilyMemoryPictureService familyMemoryPictureService) {
        this.familyMemberService = familyMemberService;
        this.haikuService = haikuService;
        this.dailyThoughtService = dailyThoughtService;
        this.familyMemoryPictureService = familyMemoryPictureService;
    }

    @GetMapping("details")
    public ResponseEntity<FamilyMemberDto> getCurrentUserDto(Principal principal) {
        try {
            return ResponseEntity.ok(familyMemberService.getMemberDto(principal));

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("haikus")
    public ResponseEntity<FamilyMemberOneTypeDto> getMemberHaikuDto(Principal principal) {
        return ResponseEntity.ok(familyMemberService.getMemberHaikuDto(principal.getName()));
    }

    @GetMapping("thoughts")
    public ResponseEntity<FamilyMemberOneTypeDto> getMemberDailyThoughtDto(Principal principal) {
        return ResponseEntity.ok(familyMemberService.getMemberDailyThoughtsDto(principal.getName()));
    }

    @GetMapping("memorypics")
    public ResponseEntity<FamilyMemberOneTypeDto> getMemberMemoryPicsDto(Principal principal) {
        return ResponseEntity.ok(familyMemberService.getMemberMemoryPicsDto(principal.getName()));
    }

    @PostMapping("/haiku/public/{id}")
    public ResponseEntity<String> makeHaikuPublic(@PathVariable Long id, Principal principal){
            return ResponseEntity.ok(haikuService.makePublic(id, principal.getName()));
    }

    @PostMapping("/haiku/share/{id}")
    public ResponseEntity<String> shareHaiku(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(haikuService.makeShared(id, principal.getName()));
    }


    @PostMapping("/thought/public/{id}")
    public ResponseEntity<String> makeThoughtPublic(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(dailyThoughtService.makePublic(id, principal.getName()));
    }

    @PostMapping("/thought/share/{id}")
    public ResponseEntity<String> shareThought(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(dailyThoughtService.makeShared(id, principal.getName()));
    }
    @PostMapping("/memorypic/public/{id}")
    public ResponseEntity<String> makeMemoryPicPublic(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(familyMemoryPictureService.makePublic(id, principal.getName()));
    }

    @PostMapping("/memorypic/share/{id}")
    public ResponseEntity<String> shareMemoryPic(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(familyMemoryPictureService.makeShared(id, principal.getName()));
    }

    @PostMapping("/haiku/pin/{id}")
    public ResponseEntity<String> pinHaiku(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(haikuService.markAsPinned(id, principal.getName()));
    }

    @PostMapping("/haiku/unpin/{id}")
    public ResponseEntity<String> unpinHaiku(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(haikuService.unpin(id, principal.getName()));
    }

    @PostMapping("/thought/pin/{id}")
    public ResponseEntity<String> pinThought(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(dailyThoughtService.markAsPinned(id, principal.getName()));
    }

    @PostMapping("/thought/unpin/{id}")
    public ResponseEntity<String> unpinThought(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(dailyThoughtService.unpin(id, principal.getName()));
    }

    @PostMapping("/memorypic/pin/{id}")
    public ResponseEntity<String> pinPicture(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(familyMemoryPictureService.markAsPinned(id, principal.getName()));
    }

    @PostMapping("/memorypic/unpin/{id}")
    public ResponseEntity<String> unpinPicture(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(familyMemoryPictureService.unpin(id, principal.getName()));
    }

}
