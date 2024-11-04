package com.jux.familyspace.Interface;

import com.jux.familyspace.Model.FamilyElementType;
import com.jux.familyspace.Model.FamilyMemberElement;

public interface FamilyElementServiceInterface<T extends FamilyMemberElement> {

    Iterable<T> getAllElements();

    T getElement(Long id);

    String addElement(T element);


}
