package com.jux.familyspace.service;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.component.ElementAdder;
import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.model.FamilyMemoryPicture;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import com.jux.familyspace.repository.FamilyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyMemoryPictureService implements FamilyElementServiceInterface<FamilyMemoryPicture> {

    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;
    private final FamilyUserRepository familyUserRepository;
    private final FamilyMemberService familyMemberService;
    private final FamilyMemberRepository familyMemberRepository;
    private final ElementAdder elementAdder;

    @Override
    public Iterable<FamilyMemoryPicture> getAllElements() {
        return familyMemoryPictureRepository.findAll();
    }

    @Override
    public Iterable<FamilyMemoryPicture> getAllElementsByDate(Date date) {
        return familyMemoryPictureRepository.getFamilyMemoryPicturesByDate(date);
    }

    @Override
    public FamilyMemoryPicture getElement(Long id) {
        return familyMemoryPictureRepository.findById(id).orElse(null);
    }

    @Override
    public String addElement(FamilyMemoryPicture element) {
        return elementAdder.addElement(element);
    }

    @Override
    public String removeElement(Long id) {
        return "";
    }
}
