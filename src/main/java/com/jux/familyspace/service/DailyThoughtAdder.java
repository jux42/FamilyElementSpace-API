package com.jux.familyspace.service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.repository.DailyThoughtRepository;
import com.jux.familyspace.repository.FamilyMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class DailyThoughtAdder extends AbstractElementAdder<DailyThought> {

    private final DailyThoughtRepository dailyThoughtRepository;

    public DailyThoughtAdder(
            FamilyMemberService familyMemberService,
            FamilyMemberRepository familyMemberRepository,
            DailyThoughtRepository dailyThoughtRepository
    ) {
        super(familyMemberService, familyMemberRepository);
        this.dailyThoughtRepository = dailyThoughtRepository;
    }

    @Override
    protected void saveElement(DailyThought element) {
        dailyThoughtRepository.save(element);
    }
}