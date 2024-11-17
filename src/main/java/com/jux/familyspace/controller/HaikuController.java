package com.jux.familyspace.controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.facade.HaikuFacade;
import com.jux.familyspace.model.Haiku;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class HaikuController {

    private final FamilyElementServiceInterface<Haiku> haikuService;
    private final HaikuFacade haikuFacade;


    @GetMapping("haikus")
    public ResponseEntity<Iterable<Haiku>> getHaikus() {
        return ResponseEntity.ok(haikuService.getAllElements());
    }

    @GetMapping("haiku/{id}")
    public ResponseEntity<Haiku> getHaiku(@PathVariable Long id) {
        return ResponseEntity.ok(haikuService.getElement(id));
    }

    @PostMapping("haiku")
    public ResponseEntity<String> addHaiku(@RequestParam String line1,
                                           @RequestParam String line2,
                                           @RequestParam String line3,
                                           Principal principal) {

        return ResponseEntity.ok(haikuFacade.addHaiku(line1, line2, line3, principal.getName()));
    }

    @DeleteMapping("haiku/{id}")
    public ResponseEntity<String> deleteHaiku(@PathVariable Long id) {
        return ResponseEntity.ok(haikuService.removeElement(id));
    }


}
