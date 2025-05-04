package com.jux.familyspace.service.spaces_service;

import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.BuyList;
import com.jux.familyspace.model.spaces.PinBoard;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestComponent
public class BuyListServiceTest {

    @Mock
    private FamilyRepository familyRepository;

    @Mock
    private FamilyMemberRepository familyMemberRepository;

    @InjectMocks
    private BuyListService buyListService;

    private Family family;
    private FamilyMember familyMember;
    private BuyList buyList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        buyList = new BuyList();
        PinBoard pinBoard = PinBoard.builder()
                .buyList(buyList)
                .build();

        family = Family.builder()
                .familyName("TestFamily")
                .pinBoard(pinBoard)
                .build();

        familyMember = FamilyMember.builder()
                .familyId(1L)
                .familyId(1L)
                .build();
    }

    @Test
    @DisplayName("Should retrieve BuyList for an existing family")
    void testGetBuyList() {
        // Given
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));

        // When
        BuyList retrievedBuyList = buyListService.getBuyList(1L);

        // Then
        assertNotNull(retrievedBuyList);
        assertThat(retrievedBuyList).isEqualTo(buyList);
        verify(familyRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should add an item to BuyList for a family member")
    void testAddItemToBuyList() {
        // Given
        when(familyMemberRepository.findById(1L)).thenReturn(Optional.of(familyMember));
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));

        // When
        String response = buyListService.addItemToBuyList(1L, "Apples");

        // Then
        assertThat(response).isEqualTo("item added to buy list");
        assertThat(buyList.getItems().getFirst().getDescription()).contains("Apples");
        verify(familyRepository, times(1)).save(family);
    }

    @Test
    @DisplayName("Should remove an item from BuyList")
    void testRemoveItemFromBuyList() {
        // Given
        buyList.addItem(1L, "Apples");
        buyList.addItem(1L, "Bananas");
        when(familyMemberRepository.findById(1L)).thenReturn(Optional.of(familyMember));
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));

        // When
        String response = buyListService.removeItemFromBuyList(1L, 2L);

        // Then
        assertEquals("item removed from buy list", response);
        verify(familyRepository, times(1)).save(family);
    }

    @Test
    @DisplayName("Should throw exception if family not found")
    void testGetBuyListWithNonExistentFamily() {
        // Given
        when(familyRepository.findById(2L)).thenReturn(Optional.empty());

        // When // Then
        assertThrows(RuntimeException.class, () -> buyListService.getBuyList(2L));
    }

    @Test
    @DisplayName("Should throw exception if user does not exist")
    void testAddItemWithNonExistentUser() {
        // Given
        when(familyMemberRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(RuntimeException.class, () ->
                buyListService.addItemToBuyList(999L, "Apples")
        );

        // Then
        assertThat(exception.getMessage()).isEqualTo("user not found or not part to a family");
    }

    @Test
    @DisplayName("Should throw exception if user has no family associated")
    void testAddItemWithUserWithoutFamily() {
        // Given
        familyMember.setFamilyId(null);
        when(familyMemberRepository.findById(1L)).thenReturn(Optional.of(familyMember));

        // When
        Exception exception = assertThrows(RuntimeException.class, () ->
                buyListService.addItemToBuyList(1L, "Apples")
        );

        // Then
        assertEquals("user not found or not part to a family", exception.getMessage());
    }

    @Test
    @DisplayName("Should remove an item that does not exist without error")
    void testRemoveNonExistentItem() {
        // Given
        buyList.addItem(1L, "Bananas");
        when(familyMemberRepository.findById(1L)).thenReturn(Optional.of(familyMember));
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));

        // When
        String response = buyListService.removeItemFromBuyList(1L, 42L);

        // Then
        assertThat(response).isEqualTo("item removed from buy list");
        assertThat(buyList.getItems().contains("Apples")).isFalse();
    }

    @Test
    @DisplayName("Should throw exception if trying to remove item with non-existent user")
    void testRemoveItemWithNonExistentUser() {
        // Given
        when(familyMemberRepository.findById(42L)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(RuntimeException.class, () ->
                buyListService.removeItemFromBuyList(42L, 1L)
        );

        // Then
        assertEquals("you are not registered, or not a member of any family", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if user has no associated family while removing item")
    void testRemoveItemWithUserWithoutFamily() {
        // Given
        familyMember.setFamilyId(null);
        when(familyMemberRepository.findById(1L)).thenReturn(Optional.of(familyMember));

        // When
        Exception exception = assertThrows(RuntimeException.class, () ->
                buyListService.removeItemFromBuyList(1L, 1L)
        );

        // Then
        assertEquals("you are not registered, or not a member of any family", exception.getMessage());
    }
}


