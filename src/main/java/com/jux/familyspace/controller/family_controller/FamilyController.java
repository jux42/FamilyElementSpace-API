package com.jux.familyspace.controller.family_controller;

import com.jux.familyspace.model.family.FamilyDto;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.service.family_service.FamilyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("family")
@RequiredArgsConstructor
@Slf4j
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping
    public ResponseEntity<String> familyRegister(Principal principal, @RequestParam String familyName, @RequestParam String secret) {
        return ResponseEntity.ok(familyService.createFamily(principal.getName(), familyName, secret));
    }

    @PutMapping
    public ResponseEntity<String> joinFamily(Principal principal, @RequestParam String familyName, @RequestParam String secret) {

            return ResponseEntity.ok(familyService.joinFamily(principal.getName(), familyName, secret));
    }

    @GetMapping
    public ResponseEntity<FamilyDto> getFamilyDto(@RequestParam String familyName) {
            return ResponseEntity.ok(familyService.getFamilyDetailsDto(familyName));
    }

    @GetMapping("/pinboard/{id}")
    public ResponseEntity<PinBoard> getPinBoard(@PathVariable Long id) {

            return ResponseEntity.ok(familyService.getPinBoard(id));
    }
}


