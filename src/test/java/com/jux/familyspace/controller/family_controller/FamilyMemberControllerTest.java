package com.jux.familyspace.controller.family_controller;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemberOneTypeDto;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.model.family.FamilyMemberDto;
import com.jux.familyspace.service.family_service.FamilyMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FamilyMemberControllerTest {

    @Mock private FamilyMemberService familyMemberService;
    @Mock private FamilyElementServiceInterface<Haiku> haikuService;
    @Mock private FamilyElementServiceInterface<DailyThought> dailyThoughtService;
    @Mock private FamilyElementServiceInterface<FamilyMemoryPicture> familyMemoryPictureService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        familyMemberService = mock(FamilyMemberService.class);
        haikuService = mock(FamilyElementServiceInterface.class);
        dailyThoughtService = mock(FamilyElementServiceInterface.class);
        familyMemoryPictureService = mock(FamilyElementServiceInterface.class);

        mockMvc = MockMvcBuilders.standaloneSetup(
                new FamilyMemberController(
                        familyMemberService,
                        haikuService,
                        dailyThoughtService,
                        familyMemoryPictureService
                )).build();
    }

    // ---------- GET /user/details ----------

    @Test
    @DisplayName("Should return FamilyMemberDto on /user/details")
    void testGetCurrentUserDto_success() throws Exception {
        when(familyMemberService.getMemberDto("jux")).thenReturn(FamilyMemberDto.builder().build());

        mockMvc.perform(get("/user/details").param("name", "jux"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 204 if getMemberDto throws exception")
    void testGetCurrentUserDto_failure() throws Exception {
        when(familyMemberService.getMemberDto("jux"))
                .thenThrow(new RuntimeException("not found"));

        mockMvc.perform(get("/user/details").param("name", "jux"))
                .andExpect(status().isNoContent());
    }

    // ---------- GET: /user/haikus, thoughts, memorypics ----------

    @Test @DisplayName("Should return haiku dto for member")
    void testGetMemberHaikuDto() throws Exception {
        when(familyMemberService.getMemberHaikuDto("jux"))
                .thenReturn(FamilyMemberOneTypeDto.builder().build());

        mockMvc.perform(get("/user/haikus").principal(() -> "jux"))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("Should return daily thoughts dto for member")
    void testGetMemberDailyThoughtsDto() throws Exception {
        when(familyMemberService.getMemberDailyThoughtsDto("jux"))
                .thenReturn(FamilyMemberOneTypeDto.builder().build());

        mockMvc.perform(get("/user/thoughts").principal(() -> "jux"))
                .andExpect(status().isOk());
    }

    @Test @DisplayName("Should return memory pictures dto for member")
    void testGetMemberMemoryPicsDto() throws Exception {
        when(familyMemberService.getMemberMemoryPicsDto("jux"))
                .thenReturn(FamilyMemberOneTypeDto.builder().build());

        mockMvc.perform(get("/user/memorypics").principal(() -> "jux"))
                .andExpect(status().isOk());
    }

    // ---------- POSTs: makePublic, share, pin, unpin ----------

    @Test @DisplayName("Should make haiku public")
    void testMakeHaikuPublic() throws Exception {
        when(haikuService.makePublic(1L, "jux")).thenReturn("done");

        mockMvc.perform(post("/user/haiku/public/1").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("done"));
    }

    @Test @DisplayName("Should share haiku")
    void testShareHaiku() throws Exception {
        when(haikuService.makeShared(1L, "jux")).thenReturn("shared");

        mockMvc.perform(post("/user/haiku/share/1").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("shared"));
    }

    @Test @DisplayName("Should make thought public")
    void testMakeThoughtPublic() throws Exception {
        when(dailyThoughtService.makePublic(2L, "jux")).thenReturn("public");

        mockMvc.perform(post("/user/thought/public/2").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("public"));
    }

    @Test @DisplayName("Should share thought")
    void testShareThought() throws Exception {
        when(dailyThoughtService.makeShared(2L, "jux")).thenReturn("shared");

        mockMvc.perform(post("/user/thought/share/2").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("shared"));
    }

    @Test @DisplayName("Should make memory pic public")
    void testMakeMemoryPicPublic() throws Exception {
        when(familyMemoryPictureService.makePublic(3L, "jux")).thenReturn("public");

        mockMvc.perform(post("/user/memorypic/public/3").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("public"));
    }

    @Test @DisplayName("Should share memory pic")
    void testShareMemoryPic() throws Exception {
        when(familyMemoryPictureService.makeShared(3L, "jux")).thenReturn("shared");

        mockMvc.perform(post("/user/memorypic/share/3").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("shared"));
    }

    @Test @DisplayName("Should pin haiku")
    void testPinHaiku() throws Exception {
        when(haikuService.markAsPinned(4L, "jux")).thenReturn("pinned");

        mockMvc.perform(post("/user/haiku/pin/4").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("pinned"));
    }

    @Test @DisplayName("Should unpin haiku")
    void testUnpinHaiku() throws Exception {
        when(haikuService.unpin(4L, "jux")).thenReturn("unpinned");

        mockMvc.perform(post("/user/haiku/unpin/4").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("unpinned"));
    }

    @Test @DisplayName("Should pin thought")
    void testPinThought() throws Exception {
        when(dailyThoughtService.markAsPinned(5L, "jux")).thenReturn("pinned");

        mockMvc.perform(post("/user/thought/pin/5").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("pinned"));
    }

    @Test @DisplayName("Should unpin thought")
    void testUnpinThought() throws Exception {
        when(dailyThoughtService.unpin(5L, "jux")).thenReturn("unpinned");

        mockMvc.perform(post("/user/thought/unpin/5").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("unpinned"));
    }

    @Test @DisplayName("Should pin memory pic")
    void testPinPicture() throws Exception {
        when(familyMemoryPictureService.markAsPinned(6L, "jux")).thenReturn("pinned");

        mockMvc.perform(post("/user/memorypic/pin/6").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("pinned"));
    }

    @Test @DisplayName("Should unpin memory pic")
    void testUnpinPicture() throws Exception {
        when(familyMemoryPictureService.unpin(6L, "jux")).thenReturn("unpinned");

        mockMvc.perform(post("/user/memorypic/unpin/6").principal(() -> "jux"))
                .andExpect(status().isOk())
                .andExpect(content().string("unpinned"));
    }
}
