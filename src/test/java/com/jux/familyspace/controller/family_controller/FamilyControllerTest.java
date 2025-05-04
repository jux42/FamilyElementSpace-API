package com.jux.familyspace.controller.family_controller;

import com.jux.familyspace.configuration.GlobalExceptionHandler;
import com.jux.familyspace.model.family.FamilyDto;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.service.family_service.FamilyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FamilyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FamilyService familyService;

    @InjectMocks
    private FamilyController familyController;

    @BeforeEach
    void setUp() {
        familyService = mock(FamilyService.class);
        familyController = new FamilyController(familyService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(familyController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Should create a family successfully")
    void testFamilyRegister_success() throws Exception {
        // Given
        when(familyService.createFamily("jux", "MyFamily", "secret"))
                .thenReturn("Family successfully created");

        Principal principal = () -> "jux";

        // When // Then
        mockMvc.perform(post("/family")
                        .param("familyName", "MyFamily")
                        .param("secret", "secret")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().string("Family successfully created"));
    }

    @Test
    @DisplayName("Should join a family successfully")
    void testJoinFamily_success() throws Exception {
        // Given
        when(familyService.joinFamily("jux", "MyFamily", "secret"))
                .thenReturn("Successfully joined");

        Principal principal = () -> "jux";

        // When // Then
        mockMvc.perform(put("/family")
                        .param("familyName", "MyFamily")
                        .param("secret", "secret")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully joined"));
    }

    @Test
    @DisplayName("Should return message when joinFamily fails")
    void testJoinFamily_failure() throws Exception {
        // Given
        when(familyService.joinFamily("jux", "MyFamily", "wrong"))
                .thenThrow(new RuntimeException("Invalid secret"));

        Principal principal = () -> "jux";

        // When // Then
        mockMvc.perform(put("/family")
                        .param("familyName", "MyFamily")
                        .param("secret", "wrong")
                        .principal(principal))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Invalid secret"));
    }

    @Test
    @DisplayName("Should return FamilyDto successfully")
    void testGetFamilyDto_success() throws Exception {
        // Given
        FamilyDto dto = FamilyDto.builder().build();
        when(familyService.getFamilyDetailsDto("MyFamily")).thenReturn(dto);

        // When // Then
        mockMvc.perform(get("/family")
                        .param("familyName", "MyFamily"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Should return PinBoard successfully")
    void testGetPinBoard_success() throws Exception {
        // Given
        PinBoard board = new PinBoard();
        when(familyService.getPinBoard(42L)).thenReturn(board);

        // When // Then
        mockMvc.perform(get("/family/pinboard/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Should return 404 when getPinBoard throws exception")
    void testGetPinBoard_failure() throws Exception {
        // Given
        when(familyService.getPinBoard(999L))
                .thenThrow(new NoSuchElementException("No pinboard found"));

        // When // Then
        mockMvc.perform(get("/family/pinboard/999")
                        .param("id", String.valueOf(999L)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No pinboard found"));
    }

    @Test
    @DisplayName("Should return 404 when family not found")
    void testGetFamilyDto_NotFound() throws Exception {
        // Given
        when(familyService.getFamilyDetailsDto("UnknownFamily"))
                .thenThrow(new NoSuchElementException("Family not found"));

        // When // Then
        mockMvc.perform(get("/family").param("familyName", "UnknownFamily"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Family not found"));
    }

    @Test
    @DisplayName("Should return 400 when family name is invalid")
    void testFamilyRegister_invalidFamilyName() throws Exception {
        // Given
        Principal principal = () -> "jux";

        when(familyService.createFamily(eq("jux"), eq("invalid@@@"), eq("secret")))
                .thenThrow(new IllegalArgumentException("Invalid family name"));

        // When // Then
        mockMvc.perform(post("/family")
                        .param("familyName", "invalid@@@")
                        .param("secret", "secret")
                        .principal(principal))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid family name"));
    }

    @Test
    @DisplayName("Should return 500 when service throws unexpected error")
    void testJoinFamily_internalError() throws Exception {
        // Given
        Principal principal = () -> "jux";

        when(familyService.joinFamily("jux", "MyFamily", "secret"))
                .thenThrow(new RuntimeException("Unexpected DB error"));

        // When // Then
        mockMvc.perform(put("/family")
                        .param("familyName", "MyFamily")
                        .param("secret", "secret")
                        .principal(principal))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Unexpected DB error"));
    }

}
