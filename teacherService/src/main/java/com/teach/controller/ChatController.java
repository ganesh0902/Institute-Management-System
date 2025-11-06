package com.teach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teach.dto.MessageBody;
import com.teach.entities.Message;
import com.teach.entities.Teacher;
import com.teach.repository.TeacherRepository;
import com.teach.serviceImpl.ChatsServiceImpl;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
	
	@Autowired
	private ChatsServiceImpl service;
	
	@Autowired
	private TeacherRepository teacherRepository;
		
	@PostMapping("/conversation")
	public ResponseEntity<Long> createConversation(@RequestParam int teacherId, @RequestParam int studentId) {

		System.out.println("Conversation is Create start here");
		
		Long conversation = service.createConversation(teacherId, studentId);

		System.out.println("Conversation is create End here");

		return new ResponseEntity<Long>(conversation, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Message> saveConversation(@RequestParam("teacherId") int teacherId, @RequestParam("stuentId") int studentId, String content)
	{
		System.out.println("Conversation is Start here");
		
		Teacher teacher = this.teacherRepository.findById(teacherId).get();
	
		MessageBody messageBody = new MessageBody();
		messageBody.setContent(content);
		messageBody.setTeacher(teacher);
		messageBody.setStudent(studentId);
		
		Message saveConversation = service.saveConversation(messageBody);

		System.out.println("Conversation is End here");

		return new ResponseEntity<Message>(saveConversation, HttpStatus.OK);
	}
}