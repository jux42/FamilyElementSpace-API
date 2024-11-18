package com.jux.familyspace.service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.model.FamilyMemoryPicture;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import org.springframework.stereotype.Service;


@Service
public class FamilyMemoryPictureAdder extends AbstractElementAdder<FamilyMemoryPicture> {

    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;
    public FamilyMemoryPictureAdder(
            FamilyMemberService familyMemberService,
            FamilyMemberRepository familyMemberRepository,
            FamilyMemoryPictureRepository familyMemoryPictureRepository
    ) {
        super(familyMemberService, familyMemberRepository);
        this.familyMemoryPictureRepository = familyMemoryPictureRepository;
    }

    @Override
    protected void saveElement(FamilyMemoryPicture element) {
        familyMemoryPictureRepository.save(element);
    }

}
