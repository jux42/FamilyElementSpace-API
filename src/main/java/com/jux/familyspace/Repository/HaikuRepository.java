package com.jux.familyspace.Repository;

import com.jux.familyspace.Model.Haiku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaikuRepository extends JpaRepository<Haiku, Long> {
}
