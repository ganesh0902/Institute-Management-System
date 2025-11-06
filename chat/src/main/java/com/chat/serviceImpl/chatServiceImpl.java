package com.chat.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.config.MessageStatus;
import com.chat.dto.UserCredentialDTO;
import com.chat.entities.Conversation;
import com.chat.entities.Message;
import com.chat.repository.ConversationRepository;
import com.chat.repository.MessageRepository;
import com.chat.service.IdentityClient;
import com.chat.service.chatService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class chatServiceImpl implements chatService{

	private final ConversationRepository convRepo;
	private final MessageRepository msgRepo;
	
	@Autowired
	private final IdentityClient identityClient;

	@Override
	@Transactional
    public Conversation createOrGetConversation(int userAId, int userBId) {
        Optional<Conversation> existing = convRepo.findByParticipantIds(userAId, userBId);
        if (!existing.isEmpty() && existing.isPresent()) return existing.get();

        Conversation c = new Conversation();
        c.getParticipantIds().add(userAId);
        c.getParticipantIds().add(userBId);
        return convRepo.save(c);
    }

	@Override
	@Transactional
    public Message sendMessage(Long conversationId, int senderId, String content) {
        Conversation conversation = convRepo.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

     //   Optional<Conversation> conv = convRepo.findById(Long.valueOf(52));
        
       // Conversation conversation = conv.get();
        
        if (!conversation.getParticipantIds().contains(senderId)) {
            throw new RuntimeException("User is not part of conversation");
        }

        // Find receiver = other participant
        int receiverId = conversation.getParticipantIds()
                             .stream()
                             .filter(id -> id != senderId)
                             .findFirst()
                             .orElseThrow();

        Message msg = new Message();
        msg.setConversationId(conversationId);
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setContent(content);
        msg.setStatus(MessageStatus.SENT);
        msg.setCreatedAt(Instant.now());
        msgRepo.save(msg);

        conversation.setLastUpdated(Instant.now());
        convRepo.save(conversation);
        return msg;
    }

	@Override
	 public List<Message> getMessages(Long conversationId) {
        return msgRepo.findByConversationIdOrderByCreatedAtAsc(conversationId);
    }

	// Optional: Enrich messages with sender/receiver details from identity-service
    public List<Map<String, Object>> getMessagesWithUserDetails(Long conversationId) {
        List<Message> messages = getMessages(conversationId);
     
        Set<Integer> userIds = messages.stream()
                .flatMap(m -> Stream.of(
                    Integer.valueOf(m.getSenderId()),
                    Integer.valueOf(m.getReceiverId())
                ))
                .collect(Collectors.toSet());


         List<UserCredentialDTO> allUser = identityClient.getAllUser();
         
                
        Map<Integer, UserCredentialDTO> userMap = allUser.stream()
                .collect(Collectors.toMap(UserCredentialDTO::getId, u -> u));

        return messages.stream()
                .map(m -> Map.of(
                        "id", m.getId(),
                        "content", m.getContent(),
                        "sender", userMap.get(m.getSenderId()),
                        "receiver", userMap.get(m.getReceiverId()),
                        "createdAt", m.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}