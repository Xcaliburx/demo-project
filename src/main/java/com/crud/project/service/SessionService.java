package com.crud.project.service;

import com.crud.project.model.Session;

public interface SessionService {

    void deleteBySessionId(String sessionId);

    void deleteByUserId(String userId);

    boolean findByAuthTokenExist(String token);

    Session getBySessionId(String sessionId);

    void save(Session entity);

    void updateToken(String sessionId, String authToken, long expiredTime, long refreshTime);

    void updateBySessionId(String sessionId);
}
