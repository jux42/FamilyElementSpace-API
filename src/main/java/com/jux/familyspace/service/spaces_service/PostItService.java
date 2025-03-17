package com.jux.familyspace.service.spaces_service;


import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.model.spaces.Priority;
import com.jux.familyspace.repository.PostitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostItService {

    private final PostitRepository postitRepository;

    public String createPostIt(String author,
                               String topic,
                               String content,
                               Integer priorityLevel) {



        PostIt postIt = PostIt.builder()
                .author(author)
                .topic(topic)
                .content(content)
                .priority(
                        priorityLevel == 1 ? Priority.CRITICAL
                                : priorityLevel == 2 ? Priority.HIGH
                                : priorityLevel == 3 ? Priority.MEDIUM
                                : Priority.LOW)
                .build();

        postitRepository.save(postIt);
        return ("Post-it created : " + postIt);

    }

    public PostIt getPostIt(Long id) {

        return postitRepository.findById(id).orElseThrow(() -> new RuntimeException("Post-it not found"));
    }

    public List<PostIt> getUserPostIts(String username) {

        return postitRepository.findByAuthor(username);
    }

    public String markPostItDone(Long id) {

        try {
            PostIt postIt = getPostIt(id);
            postIt.markAsDone();
            postitRepository.save(postIt);
            return "Post-it marked as done";
        } catch (Exception e){
            return e.getMessage();
        }

    }
}
