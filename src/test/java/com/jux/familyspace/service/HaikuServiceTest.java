package com.jux.familyspace.service;

import com.jux.familyspace.component.HaikuSizeTracker;
import com.jux.familyspace.model.ElementVisibility;
import com.jux.familyspace.model.Haiku;
import com.jux.familyspace.repository.HaikuRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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


    }

    @Test
    @DisplayName("should call repository and return all haikus")
    void shouldCallRepositoryAndReturnAllHaikus() {

        //Given
        haiku1.setOwner("jux");
        haiku2.setOwner("jux");

        List<Haiku> haikuList = Arrays.asList(haiku1, haiku2);
        when( haikuRepository.getByOwner("jux")).thenReturn(haikuList);

        //When
        Iterable<Haiku> actualList = haikuService.getAllElements("jux");

        //Then
        verify(haikuRepository).getByOwner("jux");
        assertThat(actualList).isEqualTo(haikuList);
        assertThat(haikuList.size()).isEqualTo(2);
        assertThat(haikuList).containsExactly(haiku1, haiku2);

    }

    @Test
    @DisplayName("should only return haikus marked as public")
    void shouldOnlyReturnHaikusMarkedAsPublic() {

        //Given
        haiku1.setOwner("jux");
        haiku2.setOwner("jux");
        haiku1.setVisibility(ElementVisibility.PUBLIC);

        when(haikuRepository.getByVisibility(ElementVisibility.PUBLIC)).thenReturn(Arrays.asList(haiku1));

        //When
        Iterable<Haiku> publicHaikus = haikuService.getPublicElements();

        //Then

        verify(haikuRepository).getByVisibility(ElementVisibility.PUBLIC);
        assertThat(publicHaikus).isEqualTo(Arrays.asList(haiku1));
        assertThat(publicHaikus.iterator().next().getVisibility()).isEqualTo(ElementVisibility.PUBLIC);
    }
}
