package com.crud.project.repository;

import com.crud.project.model.Broker;
import com.crud.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, Long> {
    Broker findByUser(User user);
}
