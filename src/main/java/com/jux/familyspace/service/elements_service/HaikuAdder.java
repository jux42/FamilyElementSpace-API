package com.jux.familyspace.service.elements_service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.HaikuRepository;
import com.jux.familyspace.service.users_service.FamilyMemberService;
import org.springframework.stereotype.Service;

@Service
public class HaikuAdder extends AbstractElementAdder<Haiku> {

    private final HaikuRepository haikuRepository;

    public HaikuAdder(
            FamilyMemberService familyMemberService,
            FamilyMemberRepository familyMemberRepository,
            HaikuRepository haikuRepository
    ) {
        super(familyMemberService, familyMemberRepository);
        this.haikuRepository = haikuRepository;
    }

    @Override
    protected void saveElement(Haiku element) {
        haikuRepository.save(element);
    }
}
