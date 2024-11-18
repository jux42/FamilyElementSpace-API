package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.DailyThoughtSizeTracker;
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
    private final DailyThoughtAdder dailyThoughtAdder;
    private Iterable<DailyThought> dailyThoughts;


    @Override
    public Iterable<DailyThought> getAllElements() {
        synchronizeSizeTracker();
        return dailyThoughts;
    }

    @Override
    public Iterable<DailyThought> getAllElementsByDate(Date date) {
        synchronizeSizeTracker();
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
            String output = dailyThoughtAdder.addElement(element);
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

    public void synchronizeSizeTracker() {
        int actualSize = (int) dailyThoughtRepository.count();
        if (actualSize != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(actualSize);
            dailyThoughts = dailyThoughtRepository.findAll();
        }
    }
}
