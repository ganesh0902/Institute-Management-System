package com.chat.service;

import java.util.List;
import java.util.Map;

import com.chat.entities.Conversation;
import com.chat.entities.Message;

public interface chatService {

	Conversation createOrGetConversation(int userAId, int userBId);
	
	Message sendMessage(Long conversationId, int senderId, String content);
	
	List<Message> getMessages(Long conversationId);
	
	List<Map<String, Object>> getMessagesWithUserDetails(Long conversationId);
}
