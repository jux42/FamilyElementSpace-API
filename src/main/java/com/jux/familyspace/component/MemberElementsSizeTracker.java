package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.FamilyMember;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MemberElementsSizeTracker implements ElementSizeTrackerInterface<FamilyMember> {

    private final AtomicLong totalElementsSize = new AtomicLong();


    @Override
    public long getTotalSize() {
        return totalElementsSize.get();
    }

    @Override
    public void setTotalSize(long size) {
        totalElementsSize.set(size);
    }

    @Override
    public void incrementSize(int delta) {
        totalElementsSize.addAndGet(delta);
    }

    @Override
    public void decrementSize(int delta) {
        totalElementsSize.addAndGet(-delta);
    }
}
