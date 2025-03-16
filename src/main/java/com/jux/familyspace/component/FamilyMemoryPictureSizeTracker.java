package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class FamilyMemoryPictureSizeTracker implements ElementSizeTrackerInterface<FamilyMemoryPicture> {

    private final AtomicLong totalMemoryPicsSize = new AtomicLong();

    @Override
    public long getTotalSize() {
        return totalMemoryPicsSize.get();
    }

    @Override
    public void setTotalSize(long size) {
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

