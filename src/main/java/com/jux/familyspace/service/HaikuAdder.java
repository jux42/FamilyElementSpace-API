package com.jux.familyspace.service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.model.Haiku;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.HaikuRepository;
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
