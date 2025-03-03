package com.jux.familyspace.service;

import com.jux.familyspace.component.JwtUtil;
import com.jux.familyspace.model.FamilyMember;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@TestComponent
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private FamilyMemberRepository familyMemberRepository;
    @Mock
    private FamilyUserRepository familyUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    private final String username = "jux";
    private final String password = "juxPassword";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find existing user and check password OK ")
    public void testUser_IsFound_AndPasswordCheckedOk(){

        //Given
        FamilyMember familyMember = new FamilyMember();
        familyMember.setUsername(username);
        familyMember.setPassword(password);

        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.of(familyMember));
        when(passwordEncoder.matches(familyMember.getPassword(), password)).thenReturn(true);
        when(jwtUtil.generateToken(familyMember.getUsername())).thenReturn("juxToken");

        //When
        String actualToken =  authService.authenticate("jux", "juxPassword");

        //Then
        assertThat(actualToken).isEqualTo("juxToken");

    }

    @Test
    @DisplayName("Should throw runtime exception on authentication when user is not found ")
    public void testAuthenticate_User_IsNotFound_ThrowRuntimeException(){

        //Given

        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.empty());

        //When & Then
        assertThrows(RuntimeException.class, () -> authService.authenticate("jux", "juxPassword"));

    }

    @Test
    @DisplayName("Should throw runtime exception on authentication when password is not correct ")
    public void testAuthenticate_PasswordIsNotCorrect_ThrowRuntimeException(){

        //Given

        FamilyMember familyMember = new FamilyMember();
        familyMember.setUsername(username);
        familyMember.setPassword(password);

        when(familyMemberRepository.getByUsername(username)).thenReturn(Optional.of(familyMember));
        when(passwordEncoder.matches(familyMember.getPassword(), "notJuxPassword")).thenReturn(false);


        //When & Then
        assertThrows(RuntimeException.class, () -> authService.authenticate("jux", "notJuxPassword"));

    }



        @Test
        @DisplayName("should register user with password and default avatar")
        void register_ShouldSaveNewUser_WithEncodedPassword() {


            //Given
            ArgumentCaptor<FamilyMember> userCaptor = ArgumentCaptor.forClass(FamilyMember.class);
            when(familyMemberRepository.getByUsername(anyString())).thenReturn(Optional.empty());
            when(familyMemberRepository.save(userCaptor.capture())).thenReturn(new FamilyMember());

            //When
            authService.register(username, password, true);
            FamilyMember savedUser = userCaptor.getValue();

            //Then
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getUsername()).isEqualTo(username);
            assertThat(savedUser.getRole()).isEqualTo("MEMBER");
            assertThat(savedUser.getTagline()).isEqualTo("i am new here");
            assertThat(savedUser.getAvatar()).isEqualTo(loadDefaultAvatar());
        }

    @Test
    @DisplayName("should not register user ig user with same name already exists")
    void register_fails_whenAlreadyExists() {


        //Given
        FamilyMember existingFamilyMember = new FamilyMember();
        existingFamilyMember.setUsername(username);
        when(familyMemberRepository.getByUsername(username)).thenReturn(Optional.of(existingFamilyMember));

        //When
        authService.register(username, password, true);
        String output = authService.register(username, password, true);

        //Then
        assertThat(existingFamilyMember.getUsername()).isEqualTo(username);
        assertThat(output).isEqualTo("user with this name already exists !");
    }

        private byte[] loadDefaultAvatar() {
            try {
                Path imagePath = Paths.get("src/main/resources/defaultpic/default_avatar.jpeg");
                return Files.readAllBytes(imagePath);
            } catch (Exception e) {
                return "no image".getBytes();
            }
        }

    }


