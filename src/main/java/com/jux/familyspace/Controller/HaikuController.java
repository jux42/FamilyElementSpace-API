package com.jux.familyspace.Controller;

import com.jux.familyspace.Interface.FamilyElementServiceInterface;
import com.jux.familyspace.Model.Haiku;
import com.jux.familyspace.Service.HaikuProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HaikuController {

    private final FamilyElementServiceInterface<Haiku> haikuService;
    private final HaikuProxyService haikuProxyService;


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
                                           @RequestParam String line3) {

        return ResponseEntity.ok(haikuProxyService.addHaiku(line1, line2, line3));
    }

    @DeleteMapping("haiku/{id}")
    public ResponseEntity<String> deleteHaiku(@PathVariable Long id) {
        return ResponseEntity.ok(haikuService.removeElement(id));
    }
}
