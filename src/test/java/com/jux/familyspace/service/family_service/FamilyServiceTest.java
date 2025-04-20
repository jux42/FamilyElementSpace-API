package com.jux.familyspace.service.family_service;

import com.jux.familyspace.model.dtomapper.FamilyDtoMapper;
import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyDto;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FamilyServiceTest {

    @Mock
    private FamilyRepository familyRepository;

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @Mock
    private FamilyDtoMapper familyDtoMapper;

    @InjectMocks
    private FamilyService familyService;

    private Family family;
    private FamilyMember familyMember;
    private FamilyDto familyDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        family = Family.builder()
                .id(1L)
                .familyName("TestFamily")
                .secret("secret123")
                .pinBoard(new PinBoard())
                .build();

        familyMember = FamilyMember.builder()
                .familyId(1L)
                .build();

        familyMember.setId(1L);
        familyMember.setUsername("jux");

        familyDto = FamilyDto.builder()
                .id(1L)
                .familyName("TestFamily")
                .membersNames(List.of("jux"))
                .build();
    }

    @Test
    @DisplayName("Should return FamilyDto when family exists")
    void testGetFamilyDetailsDto_Success() {
        when(familyRepository.findByFamilyName("TestFamily")).thenReturn(Optional.of(family));
        when(familyDtoMapper.mapDto(family)).thenReturn(familyDto);

        FamilyDto result = familyService.getFamilyDetailsDto("TestFamily");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(familyDto);
        verify(familyRepository, times(1)).findByFamilyName("TestFamily");
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when family does not exist")
    void testGetFamilyDetailsDto_FamilyNotFound() {
        when(familyRepository.findByFamilyName("NonExistentFamily")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> familyService.getFamilyDetailsDto("NonExistentFamily"))
                .isInstanceOf(NoSuchElementException.class);

        verify(familyRepository, times(1)).findByFamilyName("NonExistentFamily");
    }

    @Test
    @DisplayName("Should create a family successfully")
    void testCreateFamily_Success() {
        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.of(familyMember));
        when(familyRepository.findByFamilyName("TestFamily")).thenReturn(Optional.empty());

        String result = familyService.createFamily("jux", "TestFamily", "secret123");

        assertThat(result).isEqualTo("Family sucessfully created");
        verify(familyRepository, times(1)).save(any(Family.class));
        verify(familyMemberRepository, times(1)).save(any(FamilyMember.class));
    }

    @Test
    @DisplayName("Should suggest a new name when family name already exists")
    void testCreateFamily_NameAlreadyExists() {
        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.of(familyMember));
        when(familyRepository.findByFamilyName("TestFamily")).thenReturn(Optional.of(family));
        when(familyRepository.findByFamilyName("TestFamily1")).thenReturn(Optional.empty());

        String result = familyService.createFamily("jux", "TestFamily", "secret123");

        assertThat(result).startsWith("this family name already exists !");
    }

    @Test
    @DisplayName("Should return error when user is not registered")
    void testCreateFamily_UserNotRegistered() {
        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.empty());

        String result = familyService.createFamily("jux", "TestFamily", "secret123");

        assertThat(result).isEqualTo("you are not a registered user. Please register first");
    }

    @Test
    @DisplayName("Should join family successfully with correct secret")
    void testJoinFamily_Success() {
        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.of(familyMember));
        when(familyRepository.findByFamilyName("TestFamily")).thenReturn(Optional.of(family));

        String result = familyService.joinFamily("jux", "TestFamily", "secret123");

        assertThat(result).isEqualTo("welcome to the TestFamily family !");
        verify(familyRepository, times(1)).save(family);
        verify(familyMemberRepository, times(1)).save(familyMember);
    }

    @Test
    @DisplayName("Should fail to join family with incorrect secret")
    void testJoinFamily_IncorrectSecret() {
        when(familyMemberRepository.getByUsername("jux")).thenReturn(Optional.of(familyMember));
        when(familyRepository.findByFamilyName("TestFamily")).thenReturn(Optional.of(family));

        String result = familyService.joinFamily("jux", "TestFamily", "wrongSecret");

        assertThat(result).isEqualTo("Sorry, your secret does not match the family secret");
    }

    @Test
    @DisplayName("Should return PinBoard when family exists")
    void testGetPinBoard_Success() {
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));

        PinBoard result = familyService.getPinBoard(1L);

        assertThat(result).isEqualTo(family.getPinBoard());
    }

    @Test
    @DisplayName("Should return null when family does not exist")
    void testGetPinBoard_FamilyNotFound() {
        when(familyRepository.findById(1L)).thenReturn(Optional.empty());

        PinBoard result = familyService.getPinBoard(1L);

        assertThat(result).isNull();
    }
}
