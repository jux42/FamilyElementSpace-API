package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.DailyThoughtSizeTracker;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.model.ElementVisibility;
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
    public Iterable<DailyThought> getAllElements(String owner) {
        return dailyThoughtRepository.getByOwner(owner);
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
    public Iterable<DailyThought> getPublicElements() {
        synchronizeSizeTracker();
        return dailyThoughtRepository.getByVisibility(ElementVisibility.PUBLIC);

//        List<DailyThought> publicDailyList = new ArrayList<>();
//        this.dailyThoughts.iterator().forEachRemaining(dailyThought -> {
//            if (dailyThought.getVisibility() == ElementVisibility.PUBLIC) {
//                publicDailyList.add(dailyThought);
//            }
//        });
//        return publicDailyList;
    }

    @Override
    public Iterable<DailyThought> getSharedElements(String owner) {
        synchronizeSizeTracker();

        return dailyThoughtRepository.getByOwnerAndVisibility(owner, ElementVisibility.SHARED);
//        synchronizeSizeTracker();
//        List<DailyThought> sharedDailyList = new ArrayList<>();
//        this.dailyThoughts.iterator().forEachRemaining(dailyThought -> {
//            if (dailyThought.getVisibility() == ElementVisibility.SHARED &&
//                    dailyThought.getOwner().equals(owner)) {
//                sharedDailyList.add(dailyThought);
//            }
//        });
//        return sharedDailyList;
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
    public String removeElement(Long id, String owner) {

        try {
            dailyThoughtRepository.deleteByIdAndOwner(id, owner);
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
