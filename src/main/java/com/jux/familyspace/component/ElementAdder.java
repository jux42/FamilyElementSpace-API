package com.jux.familyspace.component;

import com.jux.familyspace.model.*;
import com.jux.familyspace.repository.DailyThoughtRepository;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import com.jux.familyspace.repository.HaikuRepository;
import com.jux.familyspace.service.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElementAdder {

    private final DailyThoughtRepository dailyThoughtRepository;
    private final FamilyMemberService familyMemberService;
    private final FamilyMemberRepository familyMemberRepository;
    private final HaikuRepository haikuRepository;
    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;
    private final StringBuilder outputBuilder =  new StringBuilder();


    public <T extends FamilyMemberElement> String addElement(T element) {
        outputBuilder.setLength(0);
        return switch (element) {
            case DailyThought dailyThought -> addElement(dailyThought);
            case Haiku haiku -> addElement(haiku);
            case FamilyMemoryPicture familyMemoryPicture -> addElement(familyMemoryPicture);
            case null, default -> {
                assert element != null;
                throw new UnsupportedOperationException("Unsupported element type: " + element.getClass().getSimpleName());
            }
        };
    }

    private String addElement(DailyThought element) {


        try {
            dailyThoughtRepository.save(element);
            outputBuilder.append("DailyTought element saved -");
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error while saving element : " + e.getMessage();
        }
        FamilyMember familyMember = familyMemberService.getCurrentUserByName(element.getOwner());
        familyMember.getElements().add(element);
        familyMemberRepository.save(familyMember);
        outputBuilder.append(" memoryPic added to user's list");
        return outputBuilder.toString();
    }

    private String addElement(Haiku haiku) {
        try {
            assert haiku != null;
            assert haiku.getLine1() != null;
            assert haiku.getLine2() != null;
            assert haiku.getLine3() != null;
            haikuRepository.save(haiku);
            outputBuilder.append("haiku saved -");
            FamilyMember familyMember = familyMemberService.getCurrentUserByName(haiku.getOwner());
            familyMember.getElements().add(haiku);
            familyMemberRepository.save(familyMember);
            outputBuilder.append(" haiku added to user's elements list");
            return outputBuilder.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ("error : " + e.getMessage());
        }
    }

    private String addElement(FamilyMemoryPicture element) {
        try {
            familyMemoryPictureRepository.save(element);
            outputBuilder.append("memoryPic element saved -");
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error while saving element : " + e.getMessage();
        }
        FamilyMember familyMember =  familyMemberService.getCurrentUserByName(element.getOwner());
        familyMember.getElements().add(element);
        familyMemberRepository.save(familyMember);
        outputBuilder.append(" memoryPic added to user's list");
        return outputBuilder.toString();
    }


}



