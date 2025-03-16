package com.jux.familyspace.service;

import com.jux.familyspace.api.AbstractElementAdder;
import com.jux.familyspace.api.ElementSizeTrackerInterface;
import com.jux.familyspace.model.elements.ElementVisibility;
import com.jux.familyspace.model.elements.FamilyElementType;
import com.jux.familyspace.model.elements.FamilyMemoryPicture;
import com.jux.familyspace.repository.FamilyMemoryPictureRepository;
import com.jux.familyspace.service.elements_service.FamilyMemoryPictureService;
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
public class FamilyMemoryPictureTest {
    @Mock
    private FamilyMemoryPictureRepository familyMemoryPictureRepository;
    @Mock
    private AbstractElementAdder<FamilyMemoryPicture> familyMemoryPictureAdder;
    @Mock
    private ElementSizeTrackerInterface<FamilyMemoryPicture> sizeTracker;
    @InjectMocks
    private FamilyMemoryPictureService familyMemoryPictureService;

    private FamilyMemoryPicture picture1;
    private FamilyMemoryPicture picture2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        picture1 = FamilyMemoryPicture.builder()
                .owner("jux")
                .picture("picture number one".getBytes())
                .id(1L)
                .familyElementType(FamilyElementType.MEMORY_PIC)
                .build();

        picture2 = FamilyMemoryPicture.builder()
                .owner("jux")
                .picture("picture number two".getBytes())
                .id(1L)
                .familyElementType(FamilyElementType.MEMORY_PIC)
                .build();



    }

    @Test
    @DisplayName("should call repository and return all family pictures")
    void testCallRepository_AndReturnAllFamilyMemoryPictures() {

        //Given
        List<FamilyMemoryPicture> familyMemoryPictureList = Arrays.asList(picture1, picture2);
        when(familyMemoryPictureRepository.getByOwner("jux")).thenReturn(familyMemoryPictureList);

        //When
        Iterable<FamilyMemoryPicture> actualList = familyMemoryPictureService.getAllElements("jux");

        //Then
        verify(familyMemoryPictureRepository).getByOwner("jux");
        assertThat(actualList).isEqualTo(familyMemoryPictureList);
        assertThat(familyMemoryPictureList.size()).isEqualTo(2);
        assertThat(familyMemoryPictureList).containsExactly(picture1, picture2);

    }

    @Test
    @DisplayName("should return one Optional family picture by its ID, or none")
    void testCallRepository_AndReturnOneFamilyMemoryPictureById() {

        //Given
        when(familyMemoryPictureRepository.findById(1L)).thenReturn(Optional.of(picture1));

        //When
        Optional<FamilyMemoryPicture> existingFamilyMemoryPicture = Optional.ofNullable(familyMemoryPictureService.getElement(picture1.getId()));
        Optional<FamilyMemoryPicture> nonExistentFamilyMemoryPicture = Optional.ofNullable(familyMemoryPictureService.getElement(42L));

        //Then

        verify(familyMemoryPictureRepository).findById(1L);
        assertThat(existingFamilyMemoryPicture).isNotEqualTo(nonExistentFamilyMemoryPicture);
        assertThat(existingFamilyMemoryPicture).isEqualTo(Optional.of(picture1));
        assertThat(nonExistentFamilyMemoryPicture).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("should only return family pictures marked as public")
    void shouldOnlyReturnFamilyMemoryPictures_MarkedAsPublic() {

        //Given

        picture2.setOwner("xour");
        picture1.setVisibility(ElementVisibility.PUBLIC);

        when(familyMemoryPictureRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(Collections.singletonList(picture1));

        //When
        Iterable<FamilyMemoryPicture> publicFamilyMemoryPictures = familyMemoryPictureService.getPublicElements();

        //Then

        verify(familyMemoryPictureRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(publicFamilyMemoryPictures).isEqualTo(Collections.singletonList(picture1));
        assertThat(publicFamilyMemoryPictures.iterator().next().getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
    }

    @Test
    @DisplayName("should return empty arraylist if no public family picture is found")
    public void TestEmptyList_NoPublicFamilyMemoryPictureFound() {

        //Given
        Iterable<FamilyMemoryPicture> publicFamilyMemoryPicturesEmpty = new ArrayList<>();

        when(familyMemoryPictureRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(publicFamilyMemoryPicturesEmpty);

        //When
        Iterable<FamilyMemoryPicture> actualPublicFamilyMemoryPictures = familyMemoryPictureService.getPublicElements();

        verify(familyMemoryPictureRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(actualPublicFamilyMemoryPictures).isEqualTo(publicFamilyMemoryPicturesEmpty);
        assertThat(actualPublicFamilyMemoryPictures.iterator().hasNext()).isFalse();
    }


    @Test
    @DisplayName("should only return family pictures marked as shared for one owner")
    void testOnlyOnlyMarkedAsShared() {

        //Given


        picture1.setVisibility(ElementVisibility.SHARED);

        doReturn(2L).when(sizeTracker).getTotalSize();
        when(familyMemoryPictureRepository.getByOwnerAndVisibility("jux", ElementVisibility.SHARED)).thenReturn(Collections.singletonList(picture1));

        //When
        Iterable<FamilyMemoryPicture> publicFamilyMemoryPictures = familyMemoryPictureService.getSharedElements("jux");

        //Then

        verify(familyMemoryPictureRepository).getByOwnerAndVisibility("jux", ElementVisibility.SHARED);
        assertThat(publicFamilyMemoryPictures).isEqualTo(Collections.singletonList(picture1));
        assertThat(publicFamilyMemoryPictures.iterator().next().getVisibility()).isEqualTo(ElementVisibility.SHARED);
    }

    @Test
    @DisplayName("should turn private family picture into public")
    void testFamilyMemoryPictureIntoPublic() {

        //Given

        picture1.setVisibility(ElementVisibility.PRIVATE);

        when(familyMemoryPictureRepository.getByIdAndOwner(picture1.getId(), "jux")).thenReturn(picture1);

        //When
        familyMemoryPictureService.makePublic(picture1.getId(), picture1.getOwner());


        //Then
        verify(familyMemoryPictureRepository).getByIdAndOwner(picture1.getId(), "jux");
        assertThat(picture1.getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
        assertThat(picture2.getVisibility()).isEqualTo(ElementVisibility.PRIVATE);

    }

    @Test
    @DisplayName("should turn private family picture into shared")
    void testFamilyMemoryPictureIntoShared() {

        //Given

        picture1.setVisibility(ElementVisibility.PRIVATE);

        when(familyMemoryPictureRepository.getByIdAndOwner(picture1.getId(), "jux")).thenReturn(picture1);

        //When
        familyMemoryPictureService.makeShared(picture1.getId(), picture1.getOwner());


        //Then
        verify(familyMemoryPictureRepository).getByIdAndOwner(picture1.getId(), "jux");
        assertThat(picture1.getVisibility()).isEqualTo(ElementVisibility.SHARED);
        assertThat(picture2.getVisibility()).isEqualTo(ElementVisibility.PRIVATE);

    }

    @Test
    @DisplayName("Should return all elements from a specified date")
    void testElements_FromDate() {

        //Given
        Date date = new Date();
        picture1.setDate(date);
        Date date2 = new Date(date.getTime() + 100);
        picture2.setDate(date2);
        FamilyMemoryPicture familyMemoryPicture3 = FamilyMemoryPicture.builder()
                .picture("picture number three".getBytes())
                .build();
        familyMemoryPicture3.setDate(date);

        when(familyMemoryPictureRepository.getFamilyMemoryPicturesByDate(date)).thenReturn(Arrays.asList(picture1, familyMemoryPicture3));


        //When

        Iterable<FamilyMemoryPicture> familyMemoryPicturesFromDate = familyMemoryPictureService.getAllElementsByDate(date);

        //Then
        verify(familyMemoryPictureRepository).getFamilyMemoryPicturesByDate(date);
        assertThat(familyMemoryPicturesFromDate)
                .isNotNull()
                .hasSize(2)
                .containsExactly(picture1, familyMemoryPicture3);

    }

    @Test
    @DisplayName("should create an family picture and call implementation of abstract adder")
    void testCreateFamilyMemoryPictureAndPersist() {

        //Given
        FamilyMemoryPicture familyMemoryPicture3 = FamilyMemoryPicture.builder()
                .picture("another picture number three".getBytes())
                .owner("jux")
                .id(3L)
                .build();

        when(familyMemoryPictureAdder.addElement(familyMemoryPicture3)).thenReturn("picture-test added");

        //When
        String actualOutput = familyMemoryPictureService.addElement(familyMemoryPicture3);

        //Then
        verify(familyMemoryPictureAdder).addElement(familyMemoryPicture3);
        assertThat(actualOutput).isEqualTo("picture-test added");

    }

    @Test
    @DisplayName("should remove familyMemoryPicture from repository by its ID and owner and leave others familyMemoryPictures untouched")
    void testRemoveFamilyMemoryPicture() {

        //Given


        when(familyMemoryPictureRepository.findById(1L)).thenReturn(Optional.of(picture1));
        when(familyMemoryPictureRepository.findById(2L)).thenReturn(Optional.of(picture2));
        doNothing().when(familyMemoryPictureRepository).deleteByIdAndOwner(1L, "jux");

        //When
        String actualOutput = familyMemoryPictureService.removeElement(1L, "jux");

        //Then
        assertThat(actualOutput).isEqualTo("Picture Deleted");
        verify(familyMemoryPictureRepository).deleteByIdAndOwner(1L, "jux");
        when(familyMemoryPictureRepository.findById(1L)).thenReturn(Optional.empty());
        assertThat(familyMemoryPictureRepository.findById(1L)).isEqualTo(Optional.empty());
        assertThat(familyMemoryPictureRepository.findById(2L)).isEqualTo(Optional.of(picture2));
    }

}
