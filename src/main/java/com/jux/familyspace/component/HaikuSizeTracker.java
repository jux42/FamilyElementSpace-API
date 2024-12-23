package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.Haiku;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class HaikuSizeTracker implements ElementSizeTrackerInterface<Haiku> {

    private final AtomicLong totalHaikusSize = new AtomicLong();

    @Override
    public long getTotalSize() {
        return totalHaikusSize.get();
    }

    @Override
    public void setTotalSize(long size) {
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