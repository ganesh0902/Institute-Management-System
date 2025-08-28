package com.teach.dto;

import com.teach.entities.Conversation;
import com.teach.entities.Teacher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MessageBody {

	private Teacher teacher; // sender
	private int student; // receiver
	private String content;
	private Conversation conversation;
}
