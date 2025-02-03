package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMemberElement;

import java.util.Date;

public interface FamilyElementServiceInterface<T extends FamilyMemberElement> {

    Iterable<T> getAllElements(String owner);

    Iterable<T> getPublicElements();

    Iterable<T> getSharedElements(String owner);

    String makePublic(Long id, String owner);

    String makeShared(Long id, String owner);

    String markAsPinned(Long id, String owner);

    String unpin(Long id, String owner);


    Iterable<T> getAllElementsByDate(Date date);

    T getElement(Long id);

    String addElement(T element);

    String removeElement(Long id, String owner);

}