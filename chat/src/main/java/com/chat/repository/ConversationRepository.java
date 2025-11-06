package com.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.entities.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c " +
           "WHERE :userAId MEMBER OF c.participantIds AND :userBId MEMBER OF c.participantIds")
    Optional<Conversation> findByParticipantIds(@Param("userAId") int userAId,
                                                @Param("userBId") int userBId);
}
