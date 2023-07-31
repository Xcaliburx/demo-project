package com.crud.project.session;

public interface SessionConsumer {

    SessionInfo getSessionInfo(String sessionId);

    SessionInfo getSessionLogin(String sessionId);

    void removeSessionById(String sessionId);
}
