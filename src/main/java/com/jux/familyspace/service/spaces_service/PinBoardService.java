package com.jux.familyspace.service.spaces_service;

import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemberElement;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.model.spaces.BuyList;
import com.jux.familyspace.model.spaces.ItemToBuy;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.repository.PinBoardRepository;
import com.jux.familyspace.service.family_service.FamilyMemberService;
import com.jux.familyspace.service.family_service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PinBoardService {

    private final BuyListService buyListService;
    private final PostItService postItService;
    private final FamilyMemberService familyMemberService;
    private final FamilyService familyService;
    private final PinBoardRepository pinBoardRepository;

    private String updateOutputMessage;

    public PinBoard initiatePinBoard(Long familyId) {

        List<PostIt> postItList = postItService.getFamilyPostIts(familyId);
        BuyList buyList = buyListService.getBuyList(familyId);
        List<Haiku> pinnedHaikus = new ArrayList<>();
        List<DailyThought> pinnedDailyThoughts = new ArrayList<>();
        List<FamilyMemoryPicture> pinnedPictures = new ArrayList<>();

        List<String> names = familyService.getFamilyDetailsDto(familyId).getMembersNames();

        names.forEach(name -> {
            pinnedHaikus.add((Haiku) familyMemberService.getMemberHaikuDto(name).getElements().stream().filter(haiku -> haiku.getPinned() == true));
            pinnedDailyThoughts.add((DailyThought) familyMemberService.getMemberDailyThoughtsDto(name)
                    .getElements().stream().filter(dailyThought -> dailyThought.getPinned() == true));
            pinnedPictures.add((FamilyMemoryPicture) familyMemberService.getMemberMemoryPicsDto(name)
                    .getElements().stream().filter(picture -> picture.getPinned() == true));
        });

        PinBoard pinBoard = PinBoard.builder()
                .familyId(familyId)
                .postIts(postItList)
                .buyList(buyList)
                .pinnedHaikus(pinnedHaikus)
                .pinnedDailyThoughts(pinnedDailyThoughts)
                .pinnedFamilyPictures(pinnedPictures)
                .build();

        pinBoardRepository.save(pinBoard);
        return pinBoard;
    }

    private void setOutputMessage(String message) {

        updateOutputMessage = message;

    }

    public String updatePinBoard(Long familyId, PostIt postIt) {

        pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
            pinBoard.addPostIt(postIt);
            pinBoardRepository.save(pinBoard);
            setOutputMessage("pinboard updated with post it : " + postIt.toString());
        });

        return updateOutputMessage;
    }

    public String updatePinBoard(Long familyId, ItemToBuy itemToBuy) {

        pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
            pinBoard.getBuyList().addItem(itemToBuy.getUserId(), itemToBuy.getDescription());
            pinBoardRepository.save(pinBoard);
            setOutputMessage("pinboard updated with item : " + itemToBuy.getUserId() + " --> " + itemToBuy.getDescription());
        });

        return updateOutputMessage;
    }

    public String updatePinBoard(Long familyId, Haiku element) {
        System.out.println("in the updater");

        if (element.getPinned() == false) {
            System.out.println("in the updater --> if");

            setOutputMessage("this haiku is not set to be pinned");
            pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
                pinBoard.removePinnedElement(element);
                pinBoardRepository.save(pinBoard);
                setOutputMessage("haiku removed from pinBoard");
            });
        }
        else {
            System.out.println("in the updater --> else");

            pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
                pinBoard.addPinnedElement(element);
                pinBoardRepository.save(pinBoard);
                setOutputMessage("pinboard updated with haiku : " + element);
            });
        }

        return updateOutputMessage;
    }

    public String updatePinBoard(Long familyId, DailyThought element) {
        System.out.println("in the updater");

        if (element.getPinned() == false) {
            System.out.println("in the updater --> if");

            setOutputMessage("this haiku is not set to be pinned");
            pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
                pinBoard.removePinnedElement(element);
                pinBoardRepository.save(pinBoard);
                setOutputMessage("haiku removed from pinBoard");
            });
        }
        else {
            System.out.println("in the updater --> else");

            pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
                pinBoard.addPinnedElement(element);
                pinBoardRepository.save(pinBoard);
                setOutputMessage("pinboard updated with haiku : " + element);
            });
        }

        return updateOutputMessage;
    }

    public String updatePinBoard(Long familyId, FamilyMemoryPicture element) {
        System.out.println("in the updater");

        if (element.getPinned() == false) {
            System.out.println("in the updater --> if");

            setOutputMessage("this haiku is not set to be pinned");
            pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
                pinBoard.removePinnedElement(element);
                pinBoardRepository.save(pinBoard);
                setOutputMessage("haiku removed from pinBoard");
            });
        }
        else {
            System.out.println("in the updater --> else");

            pinBoardRepository.findById(familyId).ifPresent(pinBoard -> {
                pinBoard.addPinnedElement(element);
                pinBoardRepository.save(pinBoard);
                setOutputMessage("pinboard updated with haiku : " + element);
            });
        }

        return updateOutputMessage;
    }
}
