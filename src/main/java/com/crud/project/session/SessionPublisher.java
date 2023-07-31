package com.crud.project.session;

public interface SessionPublisher {

    String setSession(String sessionId, String userId, String authToken, boolean isGranted);

    void deleteSession(String sessionId);

    void updateSession(String sessionId);
}
