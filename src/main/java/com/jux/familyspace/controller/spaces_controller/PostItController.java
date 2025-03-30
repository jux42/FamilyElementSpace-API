package com.jux.familyspace.controller.spaces_controller;


import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.service.spaces_service.PostItService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("postit")
@RequiredArgsConstructor
public class PostItController {

    private final PostItService postItService;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostIt>> getPostItsFromUSer(@PathVariable String username) {
        return ResponseEntity.ok(postItService.getUserPostIts(username));
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<PostIt>> getFamilyPostIts(@PathVariable Long familyId) {
        return ResponseEntity.ok(postItService.getFamilyPostIts(familyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostIt> getPostit(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(postItService.getPostIt(id));

        } catch (RuntimeException e) {
            return ResponseEntity.ok(PostIt.builder()
                    .author("system")
                    .topic("error")
                    .content(e.getMessage())
                    .done(true)
                    .build());
        }
    }

    @PostMapping
    public ResponseEntity<String> createPostit(Principal principal,
                                               @RequestParam String topic,
                                               @RequestParam String content,
                                               @RequestParam(required = false) Integer priorityLevel) {

        if (priorityLevel == null) priorityLevel = 0;
        try {
            return ResponseEntity.ok(postItService.createPostIt(principal.getName(), topic, content, priorityLevel));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> markPostitDone(@PathVariable Long id) {

        return ResponseEntity.ok(postItService.markPostItDone(id));
    }
}
