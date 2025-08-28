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
public class Message {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @ManyToOne
    private Conversation conversation;

    @ManyToOne
    private Teacher sender;

    private String content;
    
    private LocalDateTime timestamp = LocalDateTime.now();
}