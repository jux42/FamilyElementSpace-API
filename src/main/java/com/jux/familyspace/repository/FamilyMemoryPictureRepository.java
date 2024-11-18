package com.jux.familyspace.repository;

import com.jux.familyspace.model.ElementVisibility;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FamilyMemoryPictureRepository extends JpaRepository<FamilyMemoryPicture, Long> {
    Iterable<FamilyMemoryPicture> getFamilyMemoryPicturesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
    void deleteByIdAndOwner(Long id, String owner);

    Iterable<FamilyMemoryPicture> getByOwner(String owner);

    Iterable<FamilyMemoryPicture> getByVisibility(ElementVisibility visibility);

    Iterable<FamilyMemoryPicture> getByOwnerAndVisibility(String owner, ElementVisibility visibility);
}
