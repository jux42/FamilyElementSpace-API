package com.jux.familyspace.controller;


import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.facade.DailyThoughtFacade;
import com.jux.familyspace.model.DailyThought;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class DailyThoughtController {

    private final DailyThoughtFacade dailyThoughtFacade;
    private final FamilyElementServiceInterface<DailyThought> dailyThoughtService;

    @GetMapping("thoughts")
    public ResponseEntity<Iterable<DailyThought>> getThoughts() {
        return ResponseEntity.ok(dailyThoughtService.getAllElements());
    }

    @GetMapping("thoughts/date")
    public ResponseEntity<Iterable<DailyThought>> getThoughtByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(dailyThoughtService.getAllElementsByDate(date));
    }

    @GetMapping("thought/{id}")
    public ResponseEntity<DailyThought> getThought(@PathVariable Long id) {
        return ResponseEntity.ok(dailyThoughtService.getElement(id));
    }

    @PostMapping("thought")
    public ResponseEntity<String> addThought(@RequestParam String title,
                                             @RequestParam String thought,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                             Principal principal) {

        return ResponseEntity.ok(dailyThoughtFacade.addThought(title, thought, date, principal.getName()));
    }

    @DeleteMapping("thought/{id}")
    public ResponseEntity<String> deleteThought(@PathVariable Long id) {
        return ResponseEntity.ok(dailyThoughtService.removeElement(id));
    }

}
