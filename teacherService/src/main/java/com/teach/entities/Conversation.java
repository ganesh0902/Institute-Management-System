package com.teach.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Conversation {
		
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long conversationId;

    @ManyToOne
    private Teacher teacher;
    
    private int studentId;

    private LocalDateTime createdAt = LocalDateTime.now();
}