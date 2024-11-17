package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FamilyMemoryPictureSizeTracker implements ElementSizeTrackerInterface<FamilyMemoryPicture> {

    private final AtomicInteger totalSize = new AtomicInteger();

    @Override
    public int getTotalSize() {
        return totalSize.get();
    }

    @Override
    public void setTotalSize(int size) {
        totalSize.set(size);
    }

    @Override
    public void incrementSize(int delta) {
        totalSize.addAndGet(delta);
    }

    @Override
    public void decrementSize(int delta) {
        totalSize.addAndGet(-delta);
    }
}

