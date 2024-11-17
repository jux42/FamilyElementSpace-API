package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMemberElement;

public interface ElementSizeTrackerInterface<T extends FamilyMemberElement> {


    int getTotalSize();

    void setTotalSize(int size);

    void incrementSize(int delta);

    void decrementSize(int delta);

}
