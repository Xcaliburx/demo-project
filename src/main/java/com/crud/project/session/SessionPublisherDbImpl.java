package com.crud.project.session;

import com.crud.project.model.Session;
import com.crud.project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class SessionPublisherDbImpl implements SessionPublisher {

    @Autowired
    private SessionService sessionService;

    @Value("${jwt.timeout}")
    private long jwtTimeout;

    @Override
    public String setSession(String sessionId, String userId, String authToken, boolean isGranted) {
        try {
            Date currTime = new Date();
            long timeOut = currTime.getTime() + (jwtTimeout * 1000);

            sessionService.deleteByUserId(userId);

            Session entity = new Session();
            entity.setSessionId(sessionId);
            entity.setAuthToken(authToken);
            entity.setUserId(userId);
            entity.setExpiredTime(timeOut);
            entity.setRefreshTime(timeOut - 600000);
            entity.setClient(false);
            entity.setGranted(isGranted);
            sessionService.save(entity);

            return sessionId;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionService.deleteBySessionId(sessionId);
    }

    @Transactional
    @Override
    public void updateSession(String sessionId) {
        try {
            sessionService.updateBySessionId(sessionId);
        } catch (Exception e) {
            System.out.println("Error Update Session: " + e.getMessage());
        }
    }
}
