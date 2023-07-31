package com.crud.project.repository;

import com.crud.project.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionEntityRepository extends JpaRepository<Session, String> {

    @Modifying
    @Query(value = "DELETE FROM Session a WHERE a.sessionId = :sessionId")
    void deleteBySessionId(@Param("sessionId") String sessionId);

    @Modifying
    @Query(value = "DELETE FROM Session a WHERE a.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);

    @Query(value = "SELECT a FROM Session a WHERE a.sessionId = :sessionId")
    Session findBySessionId(@Param("sessionId") String sessionId);

    @Modifying
    @Query(value = "UPDATE Session a SET a.isGranted = true WHERE a.sessionId = :sessionId")
    void updateBySessionId(@Param("sessionId") String sessionId);
}
