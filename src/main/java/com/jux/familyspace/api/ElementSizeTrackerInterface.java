package com.jux.familyspace.api;

public interface ElementSizeTrackerInterface<T> {


    int getTotalSize();

    void setTotalSize(int size);

    void incrementSize(int delta);

    void decrementSize(int delta);

}
