package com.jux.familyspace.repository;

import com.jux.familyspace.model.spaces.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostitRepository extends JpaRepository<PostIt,Long> {
}
