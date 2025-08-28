package com.teach.service;

import com.teach.dto.MessageBody;
import com.teach.entities.Conversation;
import com.teach.entities.Message;

public interface ChatsService {

	//teacher is sender and student is receiver
	Conversation createConversation(int sender,int receiver); 
	
	Message saveConversation(MessageBody request);		
}