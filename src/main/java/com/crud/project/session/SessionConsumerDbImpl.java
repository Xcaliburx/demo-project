package com.crud.project.session;

import com.crud.project.model.Session;
import com.crud.project.security.jwt.JwtUtils;
import com.crud.project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class SessionConsumerDbImpl implements SessionConsumer {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.timeout}")
    private long jwtTimeout;

    @Value("${jwt.refresh}")
    private long jwtRefresh;

    @Override
    public SessionInfo getSessionInfo(String sessionId) {
        try {
            Date currTime = new Date();
            SessionInfo auth = null;
            Session entity = sessionService.getBySessionId(sessionId);

            if (entity != null) {
                auth = new SessionInfo();
                auth.setAuthToken(entity.getAuthToken());
                auth.setUserId(entity.getUserId());
                auth.setExpiredTime(Long.toString(entity.getExpiredTime()));

                long currDate = new Date().getTime();
                if (!entity.isGranted() || currDate > entity.getExpiredTime()) {
                    auth = null;
                } else if (currDate >= entity.getRefreshTime() && currDate <= entity.getExpiredTime()) {
                    String authToken = refreshToken(sessionId, entity.getAuthToken(), currTime);
                    auth.setAuthToken(authToken);
                }
            }
            return auth;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SessionInfo getSessionLogin(String sessionId) {
        try {
            Date currTime = new Date();
            SessionInfo auth = null;
            Session entity = sessionService.getBySessionId(sessionId);

            if (entity != null) {
                auth = new SessionInfo();
                auth.setUserId(entity.getUserId());

                long currDate = new Date().getTime();
                if (currDate >= entity.getRefreshTime() && currDate <= entity.getExpiredTime()) {
                    String authToken = refreshToken(sessionId, entity.getAuthToken(), currTime);
                    auth.setAuthToken(authToken);
                } else if (currDate > entity.getExpiredTime()) {
                    auth = null;
                } else {
                    auth.setAuthToken(entity.getAuthToken());
                }
            }
            return auth;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void removeSessionById(String sessionId) {
        sessionService.deleteBySessionId(sessionId);
    }

    private String refreshToken(String sessionId, String authToken, Date currTime) throws Exception {
        long timeOut = currTime.getTime() + (jwtTimeout * 1000);
        sessionService.updateToken(sessionId, authToken, timeOut, timeOut - (jwtRefresh * 1000));
        return authToken;
    }
}
