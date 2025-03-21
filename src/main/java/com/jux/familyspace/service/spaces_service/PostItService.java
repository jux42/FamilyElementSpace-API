package com.jux.familyspace.service.spaces_service;


import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.model.spaces.Priority;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.PostItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostItService {

    private final PostItRepository postItRepository;
    private final FamilyMemberRepository familyMemberRepository;


    public String createPostIt(String author,
                               String topic,
                               String content,
                               Integer priorityLevel) {

        Family family = familyMemberRepository.getByUsername(author)
                .map(FamilyMember::getFamily)
                .orElseThrow(() -> new RuntimeException("unknown author : " + author));


        PostIt postIt = PostIt.builder()
                .author(author)
                .family(family)
                .topic(topic)
                .content(content)
                .priority(
                        priorityLevel == 1 ? Priority.CRITICAL
                                : priorityLevel == 2 ? Priority.HIGH
                                : priorityLevel == 3 ? Priority.MEDIUM
                                : Priority.LOW)
                .build();

        postItRepository.save(postIt);
        return ("Post-it created : " + postIt);

    }

    public PostIt getPostIt(Long id) {

        return postItRepository.findById(id).orElseThrow(() -> new RuntimeException("Post-it not found"));
    }

    public List<PostIt> getFamilyPostIts(Long familyID) {
        return postItRepository.findByFamilyId(familyID);
    }

    public List<PostIt> getUserPostIts(String username) {

        return postItRepository.findByAuthor(username);
    }

    public String markPostItDone(Long id) {

        try {
            PostIt postIt = getPostIt(id);
            postIt.markAsDone();
            postItRepository.save(postIt);
            return "Post-it marked as done";
        } catch (Exception e){
            return e.getMessage();
        }

    }
}
