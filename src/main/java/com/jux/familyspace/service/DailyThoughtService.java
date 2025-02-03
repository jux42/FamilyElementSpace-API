package com.jux.familyspace.service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.DailyThoughtSizeTracker;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.model.ElementVisibility;
import com.jux.familyspace.model.Haiku;
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
    private final AbstractElementAdder<DailyThought> dailyThoughtAdder;
    private Iterable<DailyThought> dailyThoughts;


    @Override
    public Iterable<DailyThought> getAllElements(String owner) {
        return dailyThoughtRepository.getByOwner(owner);
    }

    @Override
    public Iterable<DailyThought> getAllElementsByDate(Date date) {

         return dailyThoughtRepository.getDailyThoughtByDate(date);
    }

    @Override
    public DailyThought getElement(Long id) {
        return dailyThoughtRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<DailyThought> getPublicElements() {
        synchronizeSizeTracker();
        return dailyThoughtRepository.getByVisibility(ElementVisibility.PUBLIC);
    }

    @Override
    public Iterable<DailyThought> getSharedElements(String owner) {
        synchronizeSizeTracker();

        return dailyThoughtRepository.getByOwnerAndVisibility(owner, ElementVisibility.SHARED);

    }

    @Override
    public String makePublic(Long id, String owner) {

        try {
            DailyThought dailyThought = dailyThoughtRepository.getByIdAndOwner(id, owner);
            dailyThought.setVisibility(ElementVisibility.PUBLIC);
            dailyThoughtRepository.save(dailyThought);
            return "thought successfully made public";
        } catch (Exception e) {
            return "error making public : " + e.getMessage();
        }

    }

    @Override
    public String makeShared(Long id, String owner) {

        try {
            DailyThought dailyThought = dailyThoughtRepository.getByIdAndOwner(id, owner);
            dailyThought.setVisibility(ElementVisibility.SHARED);
            dailyThoughtRepository.save(dailyThought);
            return "haiku successfully shared";
        } catch (Exception e) {
            return "error making shared : " + e.getMessage();
        }
    }

    public String markAsPinned(Long id, String owner) {

        try {
            DailyThought dailyThought = dailyThoughtRepository.getByIdAndOwner(id, owner);
            dailyThought.setPinned(true);
            dailyThoughtRepository.save(dailyThought);
            return "Thought successfully pinned";
        }catch (Exception e){
            return "error making pinned : " + e.getMessage();
        }
    }

    public String unpin(Long id, String owner) {

        try {
            DailyThought dailyThought = dailyThoughtRepository.getByIdAndOwner(id, owner);
            dailyThought.setPinned(false);
            dailyThoughtRepository.save(dailyThought);
            return "Thought successfully unpinned";
        }catch (Exception e){
            return "error while unpinning : " + e.getMessage();
        }
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

    private void synchronizeSizeTracker() {
        int actualSize = (int) dailyThoughtRepository.count();
        if (actualSize != sizeTracker.getTotalSize()) {
            sizeTracker.setTotalSize(actualSize);
            dailyThoughts = dailyThoughtRepository.findAll();
        }
    }
}
