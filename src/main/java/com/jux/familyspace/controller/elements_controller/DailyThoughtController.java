package com.jux.familyspace.controller.elements_controller;


import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.proxy.DailyThoughtProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class DailyThoughtController {

    private final DailyThoughtProxy dailyThoughtProxy;
    private final FamilyElementServiceInterface<DailyThought> dailyThoughtService;

    @GetMapping("/thoughts")
    public ResponseEntity<Iterable<DailyThought>> getThoughts(Principal principal) {
        return ResponseEntity.ok(dailyThoughtService.getAllElements(principal.getName()));
    }

    @GetMapping("/thoughts/date")
    public ResponseEntity<Iterable<DailyThought>> getThoughtByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(dailyThoughtService.getAllElementsByDate(date));
    }

    @GetMapping("/thought/{id}")
    public ResponseEntity<DailyThought> getThought(@PathVariable Long id) {
        return ResponseEntity.ok(dailyThoughtService.getElement(id));
    }

    @PostMapping("/thought")
    public ResponseEntity<String> addThought(@RequestParam String title,
                                             @RequestParam String thought,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                             Principal principal) {

        return ResponseEntity.ok(dailyThoughtProxy.addThought(title, thought, date, principal.getName()));
    }

    @DeleteMapping("/thought/{id}")
    public ResponseEntity<String> deleteThought(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(dailyThoughtService.removeElement(id, principal.getName()));
    }

}
