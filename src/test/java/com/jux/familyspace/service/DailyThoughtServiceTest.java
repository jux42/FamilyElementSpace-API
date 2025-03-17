package com.jux.familyspace.service;


import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.component.DailyThoughtSizeTracker;
import com.jux.familyspace.model.elements.DailyThought;
import com.jux.familyspace.model.elements.ElementVisibility;
import com.jux.familyspace.model.elements.FamilyElementType;
import com.jux.familyspace.repository.DailyThoughtRepository;
import com.jux.familyspace.service.elements_service.DailyThoughtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestComponent
public class DailyThoughtServiceTest {

    @Mock
    private AbstractElementAdder<DailyThought> dailyThoughtAdder;
    @Mock
    private DailyThoughtRepository dailyThoughtRepository;
    @Mock
    private DailyThoughtSizeTracker dailyThoughtSizeTracker;
    @InjectMocks
    private DailyThoughtService dailyThoughtService;

    private DailyThought dailyThought1;
    private DailyThought dailyThought2;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dailyThought1 = DailyThought.builder()
                .id(1L)
                .title("first test thought")
                .textbody("this is my thought about this first dailythought")
                .owner("jux")
                .familyElementType(FamilyElementType.DAILY_THOUGHT)
                .build();


        dailyThought2 = DailyThought.builder()
                .id(2L)
                .title("second test thought")
                .textbody("this is my thought about this second dailythought")
                .owner("jux")
                .familyElementType(FamilyElementType.DAILY_THOUGHT)
                .build();


    }

    @Test
    @DisplayName("Should return all elements of one owner")
    public void testCallRepository_AndReturnAllThoughts() {

        //Given
        List<DailyThought> thoughtList = Arrays.asList(dailyThought1, dailyThought2);
        when(dailyThoughtRepository.getByOwner("jux")).thenReturn(thoughtList);

        //When
        Iterable<DailyThought> actualList = dailyThoughtService.getAllElements("jux");

        //Then
        verify(dailyThoughtRepository).getByOwner("jux");
        assertThat(actualList).isEqualTo(thoughtList);
        assertThat(thoughtList.size()).isEqualTo(2);
        assertThat(thoughtList).containsExactly(dailyThought1, dailyThought2);

    }

    @Test
    @DisplayName("should return one Optional thought by its ID, or none")
    void testCallRepository_AndReturnOneDailyThoughtById() {

        //Given
        when(dailyThoughtRepository.findById(1L)).thenReturn(Optional.of(dailyThought1));

        //When
        Optional<DailyThought> existingThought = Optional.ofNullable(dailyThoughtService.getElement(dailyThought1.getId()));
        Optional<DailyThought> nonExistentThought = Optional.ofNullable(dailyThoughtService.getElement(42L));

        //Then
        verify(dailyThoughtRepository).findById(1L);
        assertThat(existingThought).isNotEqualTo(nonExistentThought);
        assertThat(existingThought).isEqualTo(Optional.of(dailyThought1));
        assertThat(nonExistentThought).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("Should return all thoughts from a specified date")
    void testElements_FromDate() {

        // Given
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 100);
        dailyThought1.setDate(date);
        dailyThought2.setDate(date2);

        DailyThought dailyThought3 = DailyThought.builder()
                .id(1L)
                .title("third test thought")
                .textbody("this is my thought about this third dailythought")
                .owner("jux")
                .familyElementType(FamilyElementType.DAILY_THOUGHT)
                .date(date)
                .build();

        when(dailyThoughtRepository.getDailyThoughtByDate(date)).thenReturn(Arrays.asList(dailyThought1, dailyThought3));

        // When
        Iterable<DailyThought> thoughtsFromDate = dailyThoughtService.getAllElementsByDate(date);

        // Then
        verify(dailyThoughtRepository).getDailyThoughtByDate(date);
        assertThat(thoughtsFromDate)
                .isNotNull()
                .hasSize(2)
                .containsExactly(dailyThought1, dailyThought3);
    }

    @Test
    @DisplayName("should only return thoughts marked as public")
    void shouldOnlyReturnDailyThoughts_MarkedAsPublic() {

        //Given

        dailyThought2.setOwner("xour");
        dailyThought1.setVisibility(ElementVisibility.PUBLIC);

        when(dailyThoughtRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(Collections.singletonList(dailyThought1));

        //When
        Iterable<DailyThought> publicThoughts = dailyThoughtService.getPublicElements();

        //Then

        verify(dailyThoughtRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(publicThoughts).isEqualTo(Collections.singletonList(dailyThought1));
        assertThat(publicThoughts.iterator().next().getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
    }

    @Test
    @DisplayName("should return empty arraylist if no public thought is found")
    public void TestEmptyList_NoPublicDailyThoughtFound() {

        //Given
        Iterable<DailyThought> publicThoughtsEmpty = new ArrayList<>();

        when(dailyThoughtRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(publicThoughtsEmpty);

        //When
        Iterable<DailyThought> actualPublicThoughts = dailyThoughtService.getPublicElements();

        verify(dailyThoughtRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(actualPublicThoughts).isEqualTo(publicThoughtsEmpty);
        assertThat(actualPublicThoughts.iterator().hasNext()).isFalse();
    }

    @Test
    @DisplayName("should only return DailyThoughts marked as shared for one owner")
    void testOnlyOnlyMarkedAsShared() {

        //Given


        dailyThought1.setVisibility(ElementVisibility.SHARED);

        doReturn(2L).when(dailyThoughtSizeTracker).getTotalSize();
        when(dailyThoughtRepository.getByOwnerAndVisibility("jux", ElementVisibility.SHARED)).thenReturn(Collections.singletonList(dailyThought1));

        //When
        Iterable<DailyThought> publicthoughts = dailyThoughtService.getSharedElements("jux");

        //Then

        verify(dailyThoughtRepository).getByOwnerAndVisibility("jux", ElementVisibility.SHARED);
        assertThat(publicthoughts).isEqualTo(Collections.singletonList(dailyThought1));
        assertThat(publicthoughts.iterator().next().getVisibility()).isEqualTo(ElementVisibility.SHARED);
    }

    @Test
    @DisplayName("should turn private DailyThought into public")
    void testDailyThoughtIntoPublic() {

        //Given

        dailyThought1.setVisibility(ElementVisibility.PRIVATE);

        when(dailyThoughtRepository.getByIdAndOwner(dailyThought1.getId(), "jux")).thenReturn(dailyThought1);

        //When
        dailyThoughtService.makePublic(dailyThought1.getId(), dailyThought1.getOwner());


        //Then
        verify(dailyThoughtRepository).getByIdAndOwner(dailyThought1.getId(), "jux");
        assertThat(dailyThought1.getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
        assertThat(dailyThought2.getVisibility()).isEqualTo(ElementVisibility.PRIVATE);

    }

    @Test
    @DisplayName("should turn private DailyThought into shared")
    void testDailyThoughtIntoShared() {

        //Given

        dailyThought1.setVisibility(ElementVisibility.PRIVATE);

        when(dailyThoughtRepository.getByIdAndOwner(dailyThought1.getId(), "jux")).thenReturn(dailyThought1);

        //When
        dailyThoughtService.makeShared(dailyThought1.getId(), dailyThought1.getOwner());


        //Then
        verify(dailyThoughtRepository).getByIdAndOwner(dailyThought1.getId(), "jux");
        assertThat(dailyThought1.getVisibility()).isEqualTo(ElementVisibility.SHARED);
        assertThat(dailyThought2.getVisibility()).isEqualTo(ElementVisibility.PRIVATE);

    }

    @Test
    @DisplayName("should create an DailyThought and call implementation of abstract adder")
    void testCreateDailyThoughtAndPersist() {

        //Given
        DailyThought DailyThought3 = DailyThought.builder()
                .id(1L)
                .title("yet another third test thought")
                .textbody("this is my thought about this moreother third dailythought")
                .owner("jux")
                .familyElementType(FamilyElementType.DAILY_THOUGHT)
                .build();

        when(dailyThoughtAdder.addElement(DailyThought3)).thenReturn("DailyThought-Test added");

        //When
        String actualOutput = dailyThoughtService.addElement(DailyThought3);

        //Then
        verify(dailyThoughtAdder).addElement(DailyThought3);
        assertThat(actualOutput).isEqualTo("DailyThought-Test added");

    }

    @Test
    @DisplayName("should remove DailyThought from repository by its ID and owner and leave others DailyThoughts untouched")
    void testRemoveDailyThought()  {

        //Given
        when(dailyThoughtRepository.findById(1L)).thenReturn(Optional.of(dailyThought1));
        when(dailyThoughtRepository.findById(2L)).thenReturn(Optional.of(dailyThought2));
        doNothing().when(dailyThoughtRepository).deleteByIdAndOwner(1L, "jux");

        //When
        String actualOutput = dailyThoughtService.removeElement(1L, "jux");

        //Then
        assertThat(actualOutput).isEqualTo("Daily Thought Deleted");
        verify(dailyThoughtRepository).deleteByIdAndOwner(1L, "jux");
        when(dailyThoughtRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(dailyThoughtRepository.findById(1L)).isEqualTo(Optional.empty());
        assertThat(dailyThoughtRepository.findById(2L)).isEqualTo(Optional.of(dailyThought2));


    }

}




