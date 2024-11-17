package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.Haiku;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HaikuSizeTracker implements ElementSizeTrackerInterface<Haiku> {

    private final AtomicInteger totalHaikusSize = new AtomicInteger();

    @Override
    public int getTotalSize() {
        return totalHaikusSize.get();
    }

    @Override
    public void setTotalSize(int size) {
        totalHaikusSize.set(size);
    }

    @Override
    public void incrementSize(int delta) {
        totalHaikusSize.addAndGet(delta);
    }

    @Override
    public void decrementSize(int delta) {
        totalHaikusSize.addAndGet(-delta);
    }
}