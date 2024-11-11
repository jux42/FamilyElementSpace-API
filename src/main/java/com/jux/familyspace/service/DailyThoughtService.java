package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.repository.DailyThoughtRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DailyThoughtService implements FamilyElementServiceInterface<DailyThought> {
    private final DailyThoughtRepository dailyThoughtRepository;

    public DailyThoughtService(DailyThoughtRepository dailyThoughtRepository) {
        this.dailyThoughtRepository = dailyThoughtRepository;
    }

    @Override
    public Iterable<DailyThought> getAllElements() {
        return dailyThoughtRepository.findAll();
    }

    @Override
    public Iterable<DailyThought> getAllElementsByDate(Date date) {
        return dailyThoughtRepository.getDailyThoughtByDate(date);
    }

    @Override
    public DailyThought getElement(Long id) {
        return null;
    }

    @Override
    public String addElement(DailyThought element) {
        dailyThoughtRepository.save(element);
        return "thought saved";
    }

    @Override
    public String removeElement(Long id) {
        return "";
    }
}
