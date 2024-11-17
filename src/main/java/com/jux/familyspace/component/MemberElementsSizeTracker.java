package com.jux.familyspace.component;

import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.model.FamilyMemberElement;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MemberElementsSizeTracker implements ElementSizeTrackerInterface<FamilyMember> {

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
