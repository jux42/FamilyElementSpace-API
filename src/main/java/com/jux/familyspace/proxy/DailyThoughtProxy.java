package com.jux.familyspace.proxy;

import com.jux.familyspace.api.FamilyElementServiceInterface;
import com.jux.familyspace.model.elements.DailyThought;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class DailyThoughtProxy {
    private final FamilyElementServiceInterface<DailyThought> dailyThoughtService;


    public String addThought(String title,
                             String thought,
                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                             String owner) {
        DailyThought dailyThought = DailyThought.builder()
                .title(title)
                .textbody(thought)
                .build();
        dailyThought.setDate(date);
        dailyThought.setOwner(owner);

        return dailyThoughtService.addElement(dailyThought);

    }
}