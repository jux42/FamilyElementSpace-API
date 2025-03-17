package com.jux.familyspace.repository;

import com.jux.familyspace.model.family.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<Family> findByFamilyName(String familyName);
}
