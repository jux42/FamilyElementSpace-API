package com.jux.familyspace.controller.spaces_controller;


import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.service.spaces_service.PostItService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("postit")
@RequiredArgsConstructor
public class PostItController {

    private final PostItService postItService;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostIt>> getpostits(@PathVariable String username) {
        return ResponseEntity.ok(postItService.getUserPostIts(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostIt> getPostit(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(postItService.getPostIt(id));

        }catch (RuntimeException e){
            return ResponseEntity.ok(PostIt.builder()
                    .author("system")
                    .topic("error")
                    .content(e.getMessage())
                    .done(true)
                    .build());
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> createPostit(@PathVariable String username,
                                               @RequestParam String topic,
                                               @RequestParam String content,
                                               @RequestParam(required = false) Integer priorityLevel){

        if (priorityLevel == null) priorityLevel = 0;
        return ResponseEntity.ok(postItService.createPostIt(username, topic, content, priorityLevel));
    }
}
