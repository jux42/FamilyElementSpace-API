package com.jux.familyspace.service.spaces_service;

import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.spaces.BuyList;
import com.jux.familyspace.model.spaces.ItemToBuy;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.repository.DailyThoughtRepository;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import com.jux.familyspace.repository.HaikuRepository;
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
    private final PinBoardRepository pinBoardRepository;
    private final HaikuRepository haikuRepository;
    private final DailyThoughtRepository dailyThoughtRepository;
    private final FamilyMemoryPictureRepository familyMemoryPictureRepository;

    private String updateOutputMessage;

    public PinBoard initiatePinBoard(Family family) {

        List<PostIt> postItList = postItService.getFamilyPostIts(family.getId());
        BuyList buyList = new BuyList();
        buyList.setFamilyId(family.getId());
        List<Haiku> pinnedHaikus = new ArrayList<>();
        List<DailyThought> pinnedDailyThoughts = new ArrayList<>();
        List<FamilyMemoryPicture> pinnedPictures = new ArrayList<>();

        List<String> names = family.getMemberNames();

        names.forEach(name -> {

            pinnedHaikus.addAll(haikuRepository.getByOwnerAndPinned(name, true));
            pinnedDailyThoughts.addAll(dailyThoughtRepository.getDailyThoughtsByOwnerAndPinned(name, true));
            pinnedPictures.addAll(familyMemoryPictureRepository.getByOwnerAndPinned(name, true));

        });

        PinBoard pinBoard = PinBoard.builder()
                .familyId(family.getId())
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
