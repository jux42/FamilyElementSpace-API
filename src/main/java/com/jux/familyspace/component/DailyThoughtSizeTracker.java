package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.DailyThought;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DailyThoughtSizeTracker implements ElementSizeTrackerInterface<DailyThought> {

    private final AtomicInteger totalDailyThoughtsSize = new AtomicInteger();

    @Override
    public int getTotalSize() {
        return totalDailyThoughtsSize.get();
    }

    @Override
    public void setTotalSize(int size) {
        totalDailyThoughtsSize.set(size);
    }

    @Override
    public void incrementSize(int delta) {
        totalDailyThoughtsSize.addAndGet(delta);
    }

    @Override
    public void decrementSize(int delta) {
        totalDailyThoughtsSize.addAndGet(-delta);
    }
}