package com.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entities.Conversation;
import com.chat.entities.Message;
import com.chat.serviceImpl.chatServiceImpl;

@RestController
@RequestMapping("/api/chat")
class ChatController {

	@Autowired
    private chatServiceImpl chatService;

    @PostMapping("/conversation")
    public Conversation createConversation(@RequestParam int userAId, @RequestParam int userBId) {
        return chatService.createOrGetConversation(userAId, userBId);
    }

    @PostMapping("/message")
    public Message sendMessage(@RequestParam Long conversationId,
                               @RequestParam int senderId,
                               @RequestParam String content) {
        return chatService.sendMessage(conversationId, senderId, content);
    }

    @GetMapping("/messages/{conversationId}")
    public List<Map<String, Object>> getMessages(@PathVariable Long conversationId) {
        return chatService.getMessagesWithUserDetails(conversationId);
    }
}
