package com.teach.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teach.entities.Conversation;
import com.teach.entities.Teacher;
import com.teach.repository.ConversationRepository;
import com.teach.repository.MessageRepository;
import com.teach.repository.TeacherRepository;

@Controller
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ConversationRepository conversationRepo;
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private TeacherRepository userRepo;
		
	 @PostMapping("/conversation")
	    public Conversation createConversation(@RequestParam int teacherId, @RequestParam int studentId) {
	        Optional<Conversation> existing = conversationRepo.findByTeacherIdAndStudentId(teacherId, studentId);
	        if (existing.isPresent()) return existing.get();

	        Teacher teacher = userRepo.findById(teacherId).orElseThrow();
	        
	        Conversation convo = new Conversation();
	        convo.setTeacher(teacher);
	        convo.setStudentId(studentId);
	        
	        return conversationRepo.save(convo);
	    }
}