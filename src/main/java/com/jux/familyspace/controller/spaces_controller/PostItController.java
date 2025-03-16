package com.jux.familyspace.controller.spaces_controller;


import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.repository.PostitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("postit")
@RequiredArgsConstructor
public class PostItController {

    private final PostitRepository postitRepository;

    @GetMapping("all")
    public ResponseEntity<List<PostIt>> getpostits(){
        return ResponseEntity.ok(postitRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<PostIt> createPostit(){

        int num = (int) Math.floor(Math.random()*10);
        PostIt postIt = PostIt.builder()
                .content("this is postit from API test with number: " + num)
                .author("jux")
                .build();
        return ResponseEntity.ok(postitRepository.save(postIt));
    }
}
