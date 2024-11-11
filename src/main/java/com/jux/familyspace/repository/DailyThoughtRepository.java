package com.jux.familyspace.repository;

import com.jux.familyspace.model.DailyThought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DailyThoughtRepository extends JpaRepository<DailyThought, Long> {
    Iterable<DailyThought> getDailyThoughtByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
