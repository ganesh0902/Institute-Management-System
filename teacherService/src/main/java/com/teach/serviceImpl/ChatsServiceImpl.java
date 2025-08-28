package com.teach.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teach.dto.MessageBody;
import com.teach.entities.Conversation;
import com.teach.entities.Message;
import com.teach.entities.Teacher;
import com.teach.repository.ConversationRepository;
import com.teach.repository.MessageRepository;
import com.teach.repository.TeacherRepository;
import com.teach.service.ChatsService;

@Service
public class ChatsServiceImpl implements ChatsService{

	@Autowired
	private ConversationRepository conversationRepo;
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private TeacherRepository userRepo;
	

	@Override                            //sender-teacher       //receiver student
	public Conversation createConversation(int sender, int receiver) { //check conversation is exist between teacher and student or not if not create it
		
		System.out.println("Sender Id"+sender+"Receiver Id"+receiver);
		Optional<Conversation> existing = conversationRepo.findByTeacherIdAndStudentId(sender, receiver);
		System.out.println("Conversaton Id is"+existing.get());
        if (existing.isPresent()) return existing.get();
        
        Teacher teacher = userRepo.findById(sender).orElseThrow();        
        Conversation convo = new Conversation();
        convo.setTeacher(teacher);
        convo.setStudentId(receiver);
        
        return conversationRepo.save(convo);		
	}

	@Override
	public Message saveConversation(MessageBody request) {
				
		Conversation createConversation = this.createConversation(request.getTeacher().getTId(), request.getStudent());
		
		Message message2 = new Message();
		
		message2.setSender(createConversation.getTeacher());
		message2.setMessageId(createConversation.getConversationId());
		message2.setContent(request.getContent());
		message2.setConversation(createConversation);
		return messageRepo.save(message2);
	}
}