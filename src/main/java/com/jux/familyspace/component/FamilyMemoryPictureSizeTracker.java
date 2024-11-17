package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FamilyMemoryPictureSizeTracker implements ElementSizeTrackerInterface<FamilyMemoryPicture> {

    private final AtomicInteger totalMemoryPicsSize = new AtomicInteger();

    @Override
    public int getTotalSize() {
        return totalMemoryPicsSize.get();
    }

    @Override
    public void setTotalSize(int size) {
        totalMemoryPicsSize.set(size);
    }

    @Override
    public void incrementSize(int delta) {
        totalMemoryPicsSize.addAndGet(delta);
    }

    @Override
    public void decrementSize(int delta) {
        totalMemoryPicsSize.addAndGet(-delta);
    }
}

