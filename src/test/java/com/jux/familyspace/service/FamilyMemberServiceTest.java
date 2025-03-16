package com.jux.familyspace.service;


import com.jux.familyspace.api.FamilyMemberOneTypeDtoMapperInterface;

import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.FamilyMemberOneTypeDto;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.model.elements.Haiku;
import com.jux.familyspace.model.users.FamilyMember;
import com.jux.familyspace.model.users.FamilyMemberDto;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.service.users_service.FamilyMemberService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@TestComponent
public class FamilyMemberServiceTest {

    @InjectMocks
    private FamilyMemberService familyMemberService;

    @Mock
    private FamilyMemberRepository familyMemberRepository;
    @Mock
    private FamilyMemberOneTypeDtoMapperInterface<?> dtoMapperInterface;


    private String username;
    private Haiku haiku;
    private DailyThought dailyThought1;
    private DailyThought dailyThought2;
    private FamilyMemoryPicture familyMemoryPicture;


    @BeforeEach

    void setUp() {

        MockitoAnnotations.openMocks(this);
        username = "jux";

    }

    private FamilyMember initFamilyMember() {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setId(1L);
        familyMember.setUsername(username);
        haiku = Haiku.builder()
                .line1("line1")
                .line2("line2")
                .line3("line3")
                .build();
        dailyThought1 =
                DailyThought.builder()
                        .title("title")
                        .textbody("textbody textbody textbody textbody")
                        .build();
        dailyThought2 =
                DailyThought.builder()
                        .title("title")
                        .textbody("textbody textbody textbody textbody")
                        .build();
        familyMemoryPicture =
                FamilyMemoryPicture.builder()
                        .id(1L)
                        .picture("picpicpic".getBytes())
                        .date(new Date())
                        .build();
        familyMember.addElements(haiku, dailyThought1, dailyThought2, familyMemoryPicture);
        return familyMember;
    }


    @Test
    @DisplayName("Should find and return FamilyMember object from username")
    void testGetUser_ByUsername() {

        //Given
        FamilyMember familyMember = new FamilyMember();
        familyMember.setUsername(username);
        when(familyMemberRepository.getByUsername(username)).thenReturn(Optional.of(familyMember));

        //When
        FamilyMember actualMember = familyMemberService.getCurrentUserByName(username);

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(username);
        assertThat(actualMember).isNotNull();
        assertThat(actualMember).isEqualTo(familyMember);
        assertThat(actualMember.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Should not find any FamilyMember object from username and thus return no value")
    void testGetUser_ByUsername_NoneFound() {

        //Given
        when(familyMemberRepository.getByUsername(username)).thenReturn(Optional.empty());

        //When
        FamilyMember actualMember = familyMemberService.getCurrentUserByName(username);

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(username);
        assertThat(actualMember).isNull();
    }

    @Test
    @DisplayName("should find user by username and map user to dto")
    void testGetMemberDto_ByUsername() {

        //Given
        FamilyMember familyMember = new FamilyMember();
        familyMember.setId(1L);
        familyMember.setUsername(username);
        familyMember.setPassword("password");

        FamilyMemberDto familyMemberDto = FamilyMemberDto.builder()
                .id(1L)
                .name("jux")
                .build();

        Principal principal = mock(Principal.class);

        when(familyMemberRepository.getByUsername(username)).thenReturn(Optional.of(familyMember));
        when(principal.getName()).thenReturn(username);

        //When
        FamilyMemberDto actualDto = familyMemberService.getMemberDto(principal);

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(username);
        assertThat(actualDto).isNotNull();
        assertThat(actualDto).isEqualTo(familyMemberDto);

    }

    @Test
    @DisplayName("should find user by username and map user to dto")
    void testGetMemberDto_ByUsername_NotFound() {

        //Given
        Principal principal = mock(Principal.class);

        when(familyMemberRepository.getByUsername(username)).thenReturn(Optional.empty());
        when(principal.getName()).thenReturn(username);

        //When
        FamilyMemberDto actualDto = familyMemberService.getMemberDto(principal);

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(username);
        assertThat(actualDto).isNull();

    }

    @Test
    @DisplayName("should return user Dto with only Haikus")
    void getMemberDto_ByUsername_OnlyHaikus() {

        //Given

        FamilyMember familyMember = initFamilyMember();
        Haiku haiku = Haiku.builder()
                .line1("line1")
                .line2("line2")
                .line3("line3")
                .build();

        FamilyMemberOneTypeDto familyMemberHaikuDto = FamilyMemberOneTypeDto.builder()
                .id(familyMember.getId())
                .name(familyMember.getUsername())
                .elements(List.of(haiku))
                .build();
        when(familyMemberRepository.getByUsername(familyMember.getUsername())).thenReturn(Optional.of(familyMember));
        when(dtoMapperInterface.getOneTypeMemberDto(any(FamilyMember.class))).thenReturn(familyMemberHaikuDto);
        //When
        FamilyMemberOneTypeDto actualFamilyMemberHaikuDto = familyMemberService.getMemberHaikuDto(familyMember.getUsername());
        System.out.println("testing : " + actualFamilyMemberHaikuDto);

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(familyMember.getUsername());
        assertThat(actualFamilyMemberHaikuDto).isNotNull();
        assertThat(actualFamilyMemberHaikuDto).isEqualTo(familyMemberHaikuDto);


    }

    @Test
    @DisplayName("should return user Dto with only Daily thoughts")
    void getMemberDto_ByUsername_OnlyDailyThoughts() {

        //Given

        FamilyMember familyMember =  initFamilyMember();
        FamilyMemberOneTypeDto familyMemberDailyDto = FamilyMemberOneTypeDto.builder()
                .id(familyMember.getId())
                .name(familyMember.getUsername())
                .elements(List.of(dailyThought1, dailyThought2))
                .build();
        when(familyMemberRepository.getByUsername(familyMember.getUsername())).thenReturn(Optional.of(familyMember));
        when(dtoMapperInterface.getOneTypeMemberDto(any(FamilyMember.class))).thenReturn(familyMemberDailyDto);

        //When
        FamilyMemberOneTypeDto actualFamilyMemberDailyDto = familyMemberService.getMemberDailyThoughtsDto(familyMember.getUsername());

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(familyMember.getUsername());
        assertThat(actualFamilyMemberDailyDto).isNotNull();
        assertThat(actualFamilyMemberDailyDto).isEqualTo(familyMemberDailyDto);

    }

    @Test
    @DisplayName("should return user Dto with only Pics")
    void getMemberDto_ByUsername_OnlyPics() {

        //Given
        FamilyMember familyMember =  initFamilyMember();
        FamilyMemberOneTypeDto familyMemberPicDto = FamilyMemberOneTypeDto.builder()
                .id(familyMember.getId())
                .name(familyMember.getUsername())
                .elements(List.of(familyMemoryPicture))
                .build();
        when(familyMemberRepository.getByUsername(familyMember.getUsername())).thenReturn(Optional.of(familyMember));
        when(dtoMapperInterface.getOneTypeMemberDto(any(FamilyMember.class))).thenReturn(familyMemberPicDto);

        //When
        FamilyMemberOneTypeDto actualFamilyPicDto = familyMemberService.getMemberMemoryPicsDto(familyMember.getUsername());

        //Then
        verify(familyMemberRepository, times(1)).getByUsername(familyMember.getUsername());
        assertThat(actualFamilyPicDto).isNotNull();
        assertThat(actualFamilyPicDto).isEqualTo(familyMemberPicDto);


    }

    @Test
    void testMockHaikuDtoMapper() {
        FamilyMember familyMember = initFamilyMember();
        FamilyMemberOneTypeDto expectedDto = FamilyMemberOneTypeDto.builder()
                .id(familyMember.getId())
                .name(familyMember.getUsername())
                .elements(List.of(familyMember.getElements().get(0))) // Premier élément (Haiku)
                .build();

        when(dtoMapperInterface.getOneTypeMemberDto(any(FamilyMember.class))).thenReturn(expectedDto);

        FamilyMemberOneTypeDto actualDto = dtoMapperInterface.getOneTypeMemberDto(familyMember);

        System.out.println("Test Mock Mapper - Résultat : " + actualDto);
        assertThat(actualDto).isNotNull();
        assertThat(actualDto).isEqualTo(expectedDto);
    }

}
