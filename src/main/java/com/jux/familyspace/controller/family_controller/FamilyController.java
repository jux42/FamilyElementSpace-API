package com.jux.familyspace.controller.family_controller;

import com.jux.familyspace.model.family.FamilyDto;
import com.jux.familyspace.service.family_service.FamilyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("family")
    public ResponseEntity<String> familyRegister(@RequestParam String username, @RequestParam String familyName, @RequestParam String secret) {
        return ResponseEntity.ok(familyService.createFamily(username, familyName, secret));
    }

    @PutMapping("family")
    public ResponseEntity<String> joinFamily(@RequestParam String username, @RequestParam String familyName, @RequestParam String secret) {
        try{
            return ResponseEntity.ok(familyService.joinFamily(username, familyName, secret));
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("family")
    public ResponseEntity<FamilyDto> getFamilyDto(@RequestParam String familyName) {
        try {
            return ResponseEntity.ok(familyService.getFamilyDetailsDto(familyName));

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}


