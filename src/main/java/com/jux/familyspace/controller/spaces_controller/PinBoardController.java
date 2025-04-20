package com.jux.familyspace.controller.spaces_controller;

import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.service.spaces_service.PinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PinBoardController {

    private final PinBoardService pinBoardService;

    @GetMapping("pinboard/{familyId}")
    public ResponseEntity<PinBoard> initPinBoard(@PathVariable Long familyId) {

        return ResponseEntity.ok(pinBoardService.initiatePinBoard(familyId));

    }



}
