package com.jux.familyspace.api;

import com.jux.familyspace.model.FamilyMemberElement;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.service.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractElementAdder<T extends FamilyMemberElement> {

    protected final FamilyMemberService familyMemberService;
    protected final FamilyMemberRepository familyMemberRepository;

    protected abstract void saveElement(T element);

    protected String addElementToFamilyMember(T element) {
        try {
            var familyMember = familyMemberService.getCurrentUserByName(element.getOwner());
            familyMember.getElements().add(element);
            familyMemberRepository.save(familyMember);
            return "Element added to user's list.";
        } catch (Exception e) {
            log.error("Error while adding element to family member: {}", e.getMessage());
            return "Error while adding element: " + e.getMessage();
        }
    }

    public final String addElement(T element) {
        try {
            saveElement(element);
            return addElementToFamilyMember(element);
        } catch (Exception e) {
            log.error("Error while saving element: {}", e.getMessage());
            return "Error while saving element: " + e.getMessage();
        }
    }
}
