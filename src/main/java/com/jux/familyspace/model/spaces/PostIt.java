package com.jux.familyspace.model.spaces;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostIt{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    @Builder.Default
    private Priority priority = Priority.LOW;
    private String topic;
    private String content;

    @Builder.Default
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt = new Date();
    @Builder.Default
    private Boolean done = false;


    public void markAsDone(){
        this.done = true;
    }
}
