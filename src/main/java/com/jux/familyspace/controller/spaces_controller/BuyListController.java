package com.jux.familyspace.controller.spaces_controller;

import com.jux.familyspace.model.spaces.BuyList;
import com.jux.familyspace.service.spaces_service.BuyListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buylist")
@RequiredArgsConstructor
@Slf4j
public class BuyListController {

    private final BuyListService buyListService;

    @GetMapping("/{familyId}")
    public ResponseEntity<BuyList> getBuyList(@PathVariable Long familyId) {
        return ResponseEntity.ok(buyListService.getBuyList(familyId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addItemToBuyList(@PathVariable Long userId, @RequestParam String item) {
        try {
            return ResponseEntity.ok(buyListService.addItemToBuyList(userId, item));
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{userId}/{itemId}")
    public ResponseEntity<String> removeItemFromBuyList(@PathVariable Long userId, @PathVariable Long itemId) {

        try {
            return ResponseEntity.ok(buyListService.removeItemFromBuyList(userId, itemId));
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
