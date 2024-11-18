package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMemberElement;

import java.util.Date;

public interface FamilyElementServiceInterface<T extends FamilyMemberElement> {

    Iterable<T> getAllElements();

    Iterable<T> getAllElementsByDate(Date date);

    T getElement(Long id);

    String addElement(T element);

    String removeElement(Long id);

    void synchronizeSizeTracker();

}
