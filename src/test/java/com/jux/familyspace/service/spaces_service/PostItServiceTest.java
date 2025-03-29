package com.jux.familyspace.service.spaces_service;

import com.jux.familyspace.model.family.Family;
import com.jux.familyspace.model.family.FamilyMember;
import com.jux.familyspace.model.spaces.PostIt;
import com.jux.familyspace.model.spaces.Priority;
import com.jux.familyspace.repository.FamilyMemberRepository;
import com.jux.familyspace.repository.FamilyRepository;
import com.jux.familyspace.repository.PostItRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.TestComponent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestComponent
public class PostItServiceTest {



    @InjectMocks
    private PostItService postItService;

    @Mock
    private FamilyRepository familyRepository;
    @Mock
    private FamilyMemberRepository familyMemberRepository;
    @Mock
    private PostItRepository postItRepository;

    private final String testAuthor = "jux" ;
    private final String testTopic = "test Post-it Topic" ;
    private final String testContent = "This is a Content for a test Post-it" ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should create and save postit with right attributes")
    public void testCreateAndSavePostit() {

        //Given
        Family family = Family.builder()
                .familyName("Xour")
                .id(1L)
                .build();
        FamilyMember familyMember = new FamilyMember();
        familyMember.setUsername(testAuthor);
        familyMember.setFamilyId(family.getId());

        PostIt postIt = PostIt.builder()
                .author(testAuthor)
                .familyId(family.getId())
                .topic(testTopic)
                .content(testContent)
                .priority(Priority.HIGH)
                .createdAt(new Date())
                .build();

        ArgumentCaptor<PostIt> postItCaptor = ArgumentCaptor.forClass(PostIt.class);
        when(familyMemberRepository.getByUsername(testAuthor)).thenReturn(Optional.of(familyMember));
        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));
        String expectedOutput = "Post-it created : " + postIt;

        //When
        String actualOutput = postItService.createPostIt(testAuthor, testTopic, testContent, 2);

        //Then
        verify(postItRepository, times(1)).save(postItCaptor.capture());
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("should call repository and return requested Post-it")
    public void testCallRepository_AndReturnRequestedPostIt() {

        //Given
        PostIt expectedPostIt = PostIt.builder()
                .id(1L)
                .author(testAuthor)
                .topic(testTopic)
                .content(testContent)
                .priority(Priority.HIGH)
                .build();

        when(postItRepository.findById(1L)).thenReturn(Optional.of(expectedPostIt));

        //When
        PostIt actualPostIt = postItService.getPostIt(1L);

        //Then
        verify(postItRepository, times(1)).findById(1L);
        assertThat(actualPostIt).isNotNull();
        assertThat(actualPostIt).isEqualTo(expectedPostIt);
    }


    @Test
    @DisplayName("should call repository and throw a runtime exception")
    public void testCallRepository_AndThrowsException() {

        //Given
        when(postItRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When & Then
        assertThrows(RuntimeException.class, () -> postItService.getPostIt(1L));
        verify(postItRepository).findById(1L);

    }

    @Test
    @DisplayName("should call repository and return list of one author's post-it")
    public void testCallRepository_AndReturnAuthorPostIt() {

        //Given
        PostIt expectedPostIt1 = PostIt.builder()
                .id(1L)
                .author(testAuthor)
                .topic(testTopic)
                .content(testContent)
                .priority(Priority.HIGH)
                .build();

        PostIt expectedPostIt2 = PostIt.builder()
                .id(3L)
                .author(testAuthor)
                .topic("another test topic")
                .content("this is another test topic from jux")
                .priority(Priority.MEDIUM)
                .build();

        List<PostIt> expectedPostItList = Arrays.asList(expectedPostIt1, expectedPostIt2);
        when(postItRepository.findByAuthor(testAuthor)).thenReturn(expectedPostItList);

        //When
        List<PostIt> actualPostItList = postItService.getUserPostIts(testAuthor);

        //Then
        verify(postItRepository, times(1)).findByAuthor(testAuthor);
        assertThat(actualPostItList).isEqualTo(expectedPostItList);
    }


    @Test
    @DisplayName("should find a post-it and mark a post-it as done")
    public void testCallRepository_AndMarkPostItAsDone() {

        //Given
        PostIt postit = PostIt.builder()
                .id(1L)
                .author(testAuthor)
                .topic(testTopic)
                .content(testContent)
                .done(false)
                .build();
        when(postItRepository.findById(1L)).thenReturn(Optional.of(postit));
        String expectedOutput = "Post-it marked as done";

        //When
        String actualOutput = postItService.markPostItDone(1L);

        //Then
        assertThat(actualOutput).isEqualTo(expectedOutput);
        assertThat(postit.getDone()).isTrue();

    }

    @Test
    @DisplayName("should catch exception and return failure String")
    public void testCallRepository_AndReturnFailure() {

        //Given
        PostIt postit = PostIt.builder()
                .id(1L)
                .author(testAuthor)
                .topic(testTopic)
                .content(testContent)
                .done(false)
                .build();
        when(postItRepository.findById(1L)).thenReturn(Optional.empty());
        String expectedOutput = "Post-it not found";

        //When
        String actualOutput = postItService.markPostItDone(2L);

        //Then
        assertThat(actualOutput).isEqualTo(expectedOutput);
        assertThat(postit.getDone()).isFalse();

    }
}
