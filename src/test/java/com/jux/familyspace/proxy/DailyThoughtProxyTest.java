package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.DailyThought;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestComponent
public class DailyThoughtProxyTest {

    @Mock
    private FamilyElementServiceInterface<DailyThought> dailyThoughtService;
    @InjectMocks
    private DailyThoughtProxy dailyThoughtProxy;
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date date;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        date = new Date();
    }


    @Test
    @DisplayName("should create a new Daily Thought")
    void testAddDailyThought() {

        //Given
        String title = "test title";
        String thought = "test thought";
        Date date = this.date;
        String owner = "jux";
        when(dailyThoughtService.addElement(any(DailyThought.class))).thenReturn("Test OK");

        //When
        String actualOutput = dailyThoughtProxy.addThought(title, thought, date, owner);

        //Then
        ArgumentCaptor<DailyThought> captor = ArgumentCaptor.forClass(DailyThought.class);
        verify(dailyThoughtService).addElement(captor.capture());

        DailyThought capturedThought = captor.getValue();
        assertThat(capturedThought.getTitle()).isEqualTo(title);
        assertThat(capturedThought.getTextbody()).isEqualTo(thought);
        assertThat(capturedThought.getDate()).isEqualTo(date);
        assertThat(capturedThought.getOwner()).isEqualTo(owner);
        assertThat(actualOutput).isEqualTo("Test OK");

    }

}
