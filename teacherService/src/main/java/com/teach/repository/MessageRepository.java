package com.teach.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teach.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    List<Message> findByConversationConversationIdOrderByTimestampAsc(Long conversationId);

}