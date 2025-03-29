package com.jux.familyspace.service.spaces_service;


import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.model.spaces.Priority;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyRepository;
import com.jux.familyspace.repository.PostItRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostItService {

    private final PostItRepository postItRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyRepository familyRepository;


    public String createPostIt(String author,
                               String topic,
                               String content,
                               Integer priorityLevel) {

        Optional<FamilyMember> familyMember = familyMemberRepository.getByUsername(author);
                if (familyMember.isEmpty() || familyMember.get().getFamilyId() == null) {
                    throw  new RuntimeException("unknown author : " + author);
                }
                Optional<Family> family = familyRepository.findById(familyMember.get().getFamilyId());
                if (family.isEmpty()) {
                    throw  new RuntimeException("unknown family : " + familyMember.get().getFamilyId());
                }

        PostIt postIt = PostIt.builder()
                .author(author)
                .familyId(family.get().getId())
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
