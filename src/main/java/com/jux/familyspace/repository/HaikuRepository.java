package com.jux.familyspace.repository;

import com.jux.familyspace.model.Haiku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaikuRepository extends JpaRepository<Haiku, Long> {
}
