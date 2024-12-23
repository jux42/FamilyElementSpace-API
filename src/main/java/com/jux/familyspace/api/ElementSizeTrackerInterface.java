package com.jux.familyspace.api;

public interface ElementSizeTrackerInterface<T> {


    long getTotalSize();

    void setTotalSize(long size);

    void incrementSize(int delta);

    void decrementSize(int delta);


}
