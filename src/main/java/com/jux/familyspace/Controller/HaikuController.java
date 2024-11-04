package com.jux.familyspace.Controller;

import com.jux.familyspace.Interface.FamilyElementServiceInterface;
import com.jux.familyspace.Model.Haiku;
import com.jux.familyspace.Service.HaikuProxyService;
import com.jux.familyspace.Service.HaikuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HaikuController {

    private final FamilyElementServiceInterface<Haiku> haikuService;
    private final HaikuProxyService haikuProxyService;

    public HaikuController(HaikuService haikuService, HaikuProxyService haikuProxyService) {
        this.haikuService = haikuService;
        this.haikuProxyService = haikuProxyService;
    }

    @GetMapping("haikus")
    public ResponseEntity<Iterable<Haiku>> getHaikus() {
        return ResponseEntity.ok(haikuService.getAllElements());
    }

    @PostMapping("haiku")
    public ResponseEntity<String> addHaiku(@RequestParam String line1,
                                           @RequestParam String line2,
                                           @RequestParam String line3) {

        return ResponseEntity.ok(haikuProxyService.addHaiku(line1, line2, line3));

    }
}
