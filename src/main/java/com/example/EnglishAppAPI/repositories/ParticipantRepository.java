package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Participant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("select count(p) > 0 from Participant p where p.room.id = :roomId and p.user.userId = :userId")
    boolean checkIfExistedInAnotherRoom(@Param("userId") Long userId, @Param("roomId") Long roomId);

    @Transactional
    @Modifying
    @Query("delete from Participant p where p.id = :participantId")
    void deleteAParticipant(@Param("participantId") Long participantId);
}
