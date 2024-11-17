package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.ElementAdder;
import com.jux.familyspace.model.DailyThought;
import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.repository.DailyThoughtRepository;
import com.jux.familyspace.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyThoughtService implements FamilyElementServiceInterface<DailyThought> {
    private final DailyThoughtRepository dailyThoughtRepository;
    private final FamilyMemberService familyMemberService;
    private final FamilyMemberRepository familyMemberRepository;
    private final ElementAdder elementAdder;



    @Override
    public Iterable<DailyThought> getAllElements() {
        return dailyThoughtRepository.findAll();
    }

    @Override
    public Iterable<DailyThought> getAllElementsByDate(Date date) {
        return dailyThoughtRepository.getDailyThoughtByDate(date);
    }

    @Override
    public DailyThought getElement(Long id) {
        return null;
    }

    @Override
    public String addElement(DailyThought element) {
        return elementAdder.addElement(element);
    }

    @Override
    public String removeElement(Long id) {
        return "";
    }
}
