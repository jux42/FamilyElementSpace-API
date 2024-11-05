package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMemberElement;

public interface FamilyElementServiceInterface<T extends FamilyMemberElement> {

    Iterable<T> getAllElements();

    T getElement(Long id);

    String addElement(T element);

    String removeElement(Long id);


}
