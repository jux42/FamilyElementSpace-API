package com.jux.familyspace.repository;

import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMemoryPictureRepository extends JpaRepository<FamilyMemoryPicture, Long> {
}
