package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.DailyThought;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DailyThoughtSizeTracker implements ElementSizeTrackerInterface<DailyThought> {

    private final AtomicLong totalDailyThoughtsSize = new AtomicLong();

    @Override
    public long getTotalSize() {
        return totalDailyThoughtsSize.get();
    }

    @Override
    public void setTotalSize(long size) {
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