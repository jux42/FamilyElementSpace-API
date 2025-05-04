package com.jux.familyspace.controller.family_controller;

import com.jux.familyspace.service.AuthService;
import com.jux.familyspace.service.family_service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private GuestService guestService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        guestService = mock(GuestService.class);
        authController = new AuthController(authService, guestService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("Should authenticate user and return token on /auth/login")
    void testLogin_success() throws Exception {
        when(authService.authenticate("jux", "secret")).thenReturn("token123");

        mockMvc.perform(post("/auth/login")
                        .param("username", "jux")
                        .param("password", "secret"))
                .andExpect(status().isOk())
                .andExpect(content().string("token123"));

        verify(authService).authenticate("jux", "secret");
    }

    @Test
    @DisplayName("Should register member user on /auth/memberregister")
    void testMemberRegister_success() throws Exception {
        when(authService.register("jux", "pass", true)).thenReturn("member registered");

        mockMvc.perform(post("/auth/memberregister")
                        .param("username", "jux")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(content().string("member registered"));

        verify(authService).register("jux", "pass", true);
    }

    @Test
    @DisplayName("Should login as guest on /auth/guestlogin")
    void testGuestLogin_success() throws Exception {
        when(guestService.loginAsGuest("guest123")).thenReturn("guest-token");

        mockMvc.perform(post("/auth/guestlogin")
                        .param("username", "guest123"))
                .andExpect(status().isOk())
                .andExpect(content().string("guest-token"));

        verify(guestService).loginAsGuest("guest123");
    }

    @Test
    @DisplayName("Should return guest name from Principal on /auth/getname")
    void testGetGuestName_success() throws Exception {
        Principal principal = () -> "guestName";

        mockMvc.perform(get("/auth/getname")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().string("guestName"));
    }

    @Test
    @DisplayName("Should return principal toString on /auth/whoami")
    void testWhoAmI_success() throws Exception {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "guestName";
            }

            @Override
            public String toString() {
                return "guestName";
            }
        };

        mockMvc.perform(get("/auth/whoami")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().string("guestName"));
    }



    @Test
    @DisplayName("Should return 400 if parameters are missing on /auth/login")
    void testLogin_missingParams() throws Exception {
        mockMvc.perform(post("/auth/login"))
                .andExpect(status().isBadRequest());
    }

}
