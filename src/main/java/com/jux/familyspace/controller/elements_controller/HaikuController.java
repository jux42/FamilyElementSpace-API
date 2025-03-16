package com.jux.familyspace.controller.elements_controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.proxy.HaikuProxy;
import com.jux.familyspace.model.elements.Haiku;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class HaikuController {

    private final FamilyElementServiceInterface<Haiku> haikuService;
    private final HaikuProxy haikuProxy;


    @GetMapping("haikus")
    public ResponseEntity<Iterable<Haiku>> getHaikus(Principal principal) {
        return ResponseEntity.ok(haikuService.getAllElements(principal.getName()));
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

        return ResponseEntity.ok(haikuProxy.addHaiku(line1, line2, line3, principal.getName()));
    }

    @DeleteMapping("haiku/{id}")
    public ResponseEntity<String> deleteHaiku(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(haikuService.removeElement(id, principal.getName()));
    }


}
