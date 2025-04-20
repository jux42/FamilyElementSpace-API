package com.jux.familyspace.repository;

import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.ElementVisibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyThoughtRepository extends JpaRepository<DailyThought, Long> {
    Iterable<DailyThought> getDailyThoughtByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

    void deleteByIdAndOwner(Long id, String owner);

    Iterable<DailyThought> getByOwner(String owner);

    Iterable<DailyThought> getByOwnerAndVisibility(String owner, ElementVisibility visibility);

    Iterable<DailyThought> getByVisibility(ElementVisibility elementVisibility);

    DailyThought getByIdAndOwner(Long id, String owner);

    List<DailyThought> getDailyThoughtsByOwnerAndPinned(String name, boolean b);
}
