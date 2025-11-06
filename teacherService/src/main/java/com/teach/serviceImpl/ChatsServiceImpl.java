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
	public Long createConversation(int sender, int receiver) { //check conversation is exist between teacher and student or not if not create it
		
		System.out.println("Sender Id"+sender+"Receiver Id"+receiver);
		Optional<Conversation> byTeacherIdAndStudentId = conversationRepo.findByTeacherIdAndStudentId(sender, receiver);
		
		if (!byTeacherIdAndStudentId.isEmpty() && byTeacherIdAndStudentId.isPresent()) {
			return byTeacherIdAndStudentId.get().getConversationId();
		}
        
        Teacher teacher = userRepo.findById(sender).orElseThrow();        
        Conversation convo = new Conversation();
        convo.setTeacher(teacher);
        convo.setStudentId(receiver);
        return conversationRepo.save(convo).getConversationId();
	}

	@Override
	public Message saveConversation(MessageBody request) {
				
		Long conversationId = this.createConversation(request.getTeacher().getTId(),request.getStudent());
		
		Message message2 = new Message();
		
		Optional<Conversation> con = this.conversationRepo.findById(conversationId);
		
		message2.setSender(con.get().getTeacher());
		message2.setMessageId(conversationId);
		message2.setContent(request.getContent());
		message2.setConversation(con.get());
		return messageRepo.save(message2);
	}
}