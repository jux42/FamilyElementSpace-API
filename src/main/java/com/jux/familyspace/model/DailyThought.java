package com.jux.familyspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor

public class DailyThought extends FamilyMemberElement {

    private String textbody;
    private String title;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FamilyElementType familyElementType = FamilyElementType.DAILY_THOUGHT;

    public DailyThought() {
        super.setCreationDate(new Date());
    }

}
