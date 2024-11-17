package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.DailyThoughtSizeTracker;
import com.jux.familyspace.component.ElementAdder;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.repository.DailyThoughtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyThoughtService implements FamilyElementServiceInterface<DailyThought> {
    private final DailyThoughtRepository dailyThoughtRepository;
    private final DailyThoughtSizeTracker sizeTracker;
    private final ElementAdder elementAdder;
    private Iterable<DailyThought> dailyThoughts;


    @Override
    public Iterable<DailyThought> getAllElements() {
        if (dailyThoughts == null || dailyThoughts.spliterator().getExactSizeIfKnown() != sizeTracker.getTotalSize()) {
            dailyThoughts = dailyThoughtRepository.findAll();
            return dailyThoughts;
        }
        return dailyThoughts;
    }

    @Override
    public Iterable<DailyThought> getAllElementsByDate(Date date) {
        if (dailyThoughts == null || dailyThoughts.spliterator().getExactSizeIfKnown() != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(dailyThoughtRepository.findAll().size());
            dailyThoughts = dailyThoughtRepository.findAll();
        }
        List<DailyThought> dailyThoughtList = new ArrayList<>();
        dailyThoughts.iterator().forEachRemaining(dailyThought -> {
            if (dailyThought.getDate().equals(date)) {
                dailyThoughtList.add(dailyThought);
            }
        });
        return dailyThoughtList;
    }

    @Override
    public DailyThought getElement(Long id) {
        return dailyThoughtRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(DailyThought element) {
        try {
            String output = elementAdder.addElement(element);
            sizeTracker.incrementSize(1);
            return output;
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String removeElement(Long id) {
        try {
            dailyThoughtRepository.deleteById(id);
            sizeTracker.decrementSize(1);
            return "Daily Thought Deleted";
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
}
