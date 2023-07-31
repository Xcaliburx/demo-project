package com.crud.project.service.impl;

import com.crud.project.model.Session;
import com.crud.project.repository.SessionEntityRepository;
import com.crud.project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionEntityRepository repo;

    @Transactional
    @Override
    public void deleteBySessionId(String sessionId) {
        repo.deleteBySessionId(sessionId);
    }

    @Transactional
    @Override
    public void deleteByUserId(String userId) {
        repo.deleteByUserId(userId);
    }

    @Override
    public Session getBySessionId(String sessionId) {
        return repo.findBySessionId(sessionId);
    }

    @Transactional
    @Override
    public void save(Session entity) {
        repo.save(entity);
    }

    @Transactional
    @Override
    public void updateToken(String sessionId, String authToken, long expiredTime, long refreshTime) {
        try {
            Session session = getBySessionId(sessionId);

            if (session != null) {
                session.setAuthToken(authToken);
                session.setExpiredTime(expiredTime);
                session.setRefreshTime(refreshTime);
                repo.save(session);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateBySessionId(String sessionId) {
        repo.updateBySessionId(sessionId);
    }
}
