package com.jux.familyspace.repository;

import com.jux.familyspace.model.spaces.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostitRepository extends JpaRepository<PostIt,Long> {
    List<PostIt> findByAuthor( String author);
}
