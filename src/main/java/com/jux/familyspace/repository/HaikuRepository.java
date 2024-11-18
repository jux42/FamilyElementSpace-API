package com.jux.familyspace.repository;

import com.jux.familyspace.model.ElementVisibility;
import com.jux.familyspace.model.Haiku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface HaikuRepository extends JpaRepository<Haiku, Long> {
    Iterable<Haiku> getHaikusByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
    void deleteByIdAndOwner(Long id, String owner);

    Iterable<Haiku> getByOwner(String owner);

    Iterable<Haiku> getByVisibility(ElementVisibility elementVisibility);

    Iterable<Haiku> getByOwnerAndVisibility(String owner, ElementVisibility elementVisibility);

    Haiku getByIdAndOwner(Long id, String owner);
}
