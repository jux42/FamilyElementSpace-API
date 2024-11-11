package com.jux.familyspace.controller;

import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.service.FamilyMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/user")
public class FamilyMemberController {


    private final FamilyMemberService familyMemberService;

    public FamilyMemberController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @GetMapping("details")
    public ResponseEntity<FamilyMember> getCurrentUser(Principal principal) {
        try {
            return ResponseEntity.ok(familyMemberService.getCurrentUserByName(principal.getName()));

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
}
