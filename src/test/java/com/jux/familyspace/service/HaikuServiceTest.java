package com.jux.familyspace.service;

import com.jux.familyspace.component.HaikuSizeTracker;
import com.jux.familyspace.model.ElementVisibility;
import com.jux.familyspace.model.Haiku;
import com.jux.familyspace.repository.HaikuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class HaikuServiceTest {

    @Mock
    private HaikuRepository haikuRepository;
    @Mock
    private HaikuSizeTracker sizeTracker;
    @Mock
    private HaikuAdder haikuAdder;
    @InjectMocks
    private HaikuService haikuService;

    private Haiku haiku1;
    private Haiku haiku2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        haiku1 = Haiku.builder()
                .line1("Test is running now")
                .line2("We hope that i will pass")
                .line3("if not i will cry")
                .build();

        haiku2 = Haiku.builder()
                .line1("cherry trees are cool")
                .line2("cherries are even better")
                .line3("cherry test one two")
                .build();


        haiku1.setId(1L);
        haiku2.setId(2L);

        haikuRepository.save(haiku1);
        haikuRepository.save(haiku2);
    }

    @Test
    @DisplayName("should call repository and return all haikus")
    void testCallRepository_AndReturnAllHaikus() {

        //Given
        haiku1.setOwner("jux");
        haiku2.setOwner("jux");

        List<Haiku> haikuList = Arrays.asList(haiku1, haiku2);
        when(haikuRepository.getByOwner("jux")).thenReturn(haikuList);

        //When
        Iterable<Haiku> actualList = haikuService.getAllElements("jux");

        //Then
        verify(haikuRepository).getByOwner("jux");
        assertThat(actualList).isEqualTo(haikuList);
        assertThat(haikuList.size()).isEqualTo(2);
        assertThat(haikuList).containsExactly(haiku1, haiku2);

    }

    @Test
    @DisplayName("should return one Optional haiku by its ID, or none")
    void testCallRepository_AndReturnOneHaikuById() {

        //Given
        when(haikuRepository.findById(1L)).thenReturn(Optional.of(haiku1));

        //When
        Optional<Haiku> existingHaiku = Optional.ofNullable(haikuService.getElement(haiku1.getId()));
        Optional<Haiku> nonExistentHaiku = Optional.ofNullable(haikuService.getElement(42L));

        //Then

        verify(haikuRepository).findById(1L);
        assertThat(existingHaiku).isNotEqualTo(nonExistentHaiku);
        assertThat(existingHaiku).isEqualTo(Optional.of(haiku1));
        assertThat(nonExistentHaiku).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("should only return haikus marked as public")
    void shouldOnlyReturnHaikus_MarkedAsPublic() {

        //Given
        haiku1.setOwner("jux");
        haiku2.setOwner("xour");
        haiku1.setVisibility(ElementVisibility.PUBLIC);

        when(haikuRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(Arrays.asList(haiku1));

        //When
        Iterable<Haiku> publicHaikus = haikuService.getPublicElements();

        //Then

        verify(haikuRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(publicHaikus).isEqualTo(Arrays.asList(haiku1));
        assertThat(publicHaikus.iterator().next().getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
    }

    @Test
    @DisplayName("should return empty arraylist if no public haiku is found")
    public void TestEmptyList_NoPublicHaikuFound() {

        //Given
        Iterable<Haiku> publicHaikusEmpty = new ArrayList<>();

        when(haikuRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(publicHaikusEmpty);

        //When
        Iterable<Haiku> actualPublicHaikus = haikuService.getPublicElements();

        verify(haikuRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(actualPublicHaikus).isEqualTo(publicHaikusEmpty);
        assertThat(actualPublicHaikus.iterator().hasNext()).isFalse();
    }


    @Test
    @DisplayName("should only return haikus marked as shared for one owner")
    void testOnlyOnlyMarkedAsShared() {

        //Given
        haiku1.setOwner("jux");
        haiku2.setOwner("jux");
        haiku1.setVisibility(ElementVisibility.SHARED);

        when(haikuRepository.getByOwnerAndVisibility("jux", ElementVisibility.SHARED)).thenReturn(Arrays.asList(haiku1));

        //When
        Iterable<Haiku> publicHaikus = haikuService.getSharedElements("jux");

        //Then

        verify(haikuRepository).getByOwnerAndVisibility("jux", ElementVisibility.SHARED);
        assertThat(publicHaikus).isEqualTo(Arrays.asList(haiku1));
        assertThat(publicHaikus.iterator().next().getVisibility()).isEqualTo(ElementVisibility.SHARED);
    }

    @Test
    @DisplayName("should turn private haiku into public")
    void testHaikuIntoPublic() {

        //Given
        haiku1.setOwner("jux");
        haiku1.setVisibility(ElementVisibility.PRIVATE);

        when(haikuRepository.getByIdAndOwner(haiku1.getId(), "jux")).thenReturn(haiku1);

        //When
        haikuService.makePublic(haiku1.getId(), haiku1.getOwner());


        //Then
        verify(haikuRepository).getByIdAndOwner(haiku1.getId(), "jux");
        assertThat(haiku1.getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
        assertThat(haiku2.getVisibility()).isEqualTo(ElementVisibility.PRIVATE);

    }

    @Test
    @DisplayName("should turn private haiku into shared")
    void testHaikuIntoShared() {

        //Given
        haiku1.setOwner("jux");
        haiku1.setVisibility(ElementVisibility.PRIVATE);

        when(haikuRepository.getByIdAndOwner(haiku1.getId(), "jux")).thenReturn(haiku1);

        //When
        haikuService.makeShared(haiku1.getId(), haiku1.getOwner());


        //Then
        verify(haikuRepository).getByIdAndOwner(haiku1.getId(), "jux");
        assertThat(haiku1.getVisibility()).isEqualTo(ElementVisibility.SHARED);
        assertThat(haiku2.getVisibility()).isEqualTo(ElementVisibility.PRIVATE);

    }

    @Test
    @DisplayName("Should return all elements from a specified date")
    void testElements_FromDate() throws InterruptedException {

        //Given
        Date date = new Date();
        haiku1.setDate(date);
        Thread.sleep(100);
        Date date2 = new Date();
        haiku2.setDate(date2);
        Haiku haiku3 = Haiku.builder()
                .line1("today is a day")
                .line2("tomorow is a day too")
                .line3("time is never done")
                .build();
        haiku3.setDate(date);

        when(haikuRepository.getHaikusByDate(date)).thenReturn(Arrays.asList(haiku1, haiku3));


        //When

        Iterable<Haiku> haikusFromDate = haikuService.getAllElementsByDate(date);

        //Then
        verify(haikuRepository).getHaikusByDate(date);
        assertThat(haikusFromDate).isEqualTo(Arrays.asList(haiku1, haiku3));
        assertThat(haikusFromDate).hasSize(2);
        assertThat(haiku2.getDate()).isNotEqualTo(date);

    }

    @Test
    @DisplayName("should create an haiku and call implementation of abstract adder")
    void testCreateHaikuAndPersist() {

        //Given
        Haiku haiku3 = Haiku.builder()
                .line1("i am the third one")
                .line2("but not the third man or day")
                .line3("just the third Haiku")
                .build();

        when(haikuAdder.addElement(haiku3)).thenReturn("Haiku-Test added");

        //When
        String actualOutput = haikuService.addElement(haiku3);

        //Then
        verify(haikuAdder).addElement(haiku3);
        assertThat(actualOutput).isEqualTo("Haiku-Test added");

    }

    @Test
    @DisplayName("should remove haiku from repository by its ID and owner and leave others haikus untouched")
    void testRemoveHaiku()  {

        //Given
        haiku1.setOwner("jux");
        haiku2.setOwner("jux");
        when(haikuRepository.findById(1L)).thenReturn(Optional.of(haiku1));
        when(haikuRepository.findById(2L)).thenReturn(Optional.of(haiku2));
        doNothing().when(haikuRepository).deleteByIdAndOwner(1L, "jux");

        //When
        String actualOutput = haikuService.removeElement(1L, "jux");

        //Then
        assertThat(actualOutput).isEqualTo("Haiku Deleted");
        verify(haikuRepository).deleteByIdAndOwner(1L, "jux");
        when(haikuRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(haikuRepository.findById(1L)).isEqualTo(Optional.empty());
        assertThat(haikuRepository.findById(2L)).isEqualTo(Optional.of(haiku2));


    }

    @Test
    @DisplayName("should compare tracker and repo sizes and update")
    void testCompareTrackerAndRepoSize_AndUpdate() {

        //Given
        long trackerSize = 42;
        long repoSize = 38;
        when(sizeTracker.getTotalSize()).thenReturn(trackerSize);
        when(haikuRepository.count()).thenReturn(repoSize);

        //When
        haikuService.synchronizeSizeTracker();
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(sizeTracker).setTotalSize(captor.capture());

        //Then
        verify(sizeTracker).getTotalSize();
        verify(haikuRepository).count();
        verify(sizeTracker).setTotalSize(repoSize);
        assertThat(captor.getValue()).isEqualTo(repoSize);

    }

    @Test
    @DisplayName("should do nothing if tracker size matches repo size")
    void testNoUpdateIfSizesMatch() {
        // Given
        long trackerSize = 38;
        long repoSize = 38;
        when(sizeTracker.getTotalSize()).thenReturn(trackerSize);
        when(haikuRepository.count()).thenReturn(repoSize);

        // When
        haikuService.synchronizeSizeTracker();

        // Then
        verify(sizeTracker).getTotalSize();
        verify(haikuRepository).count();
        verify(sizeTracker, never()).setTotalSize(anyLong());
        verify(haikuRepository, never()).findAll();
    }




}
