package com.crud.project.repository;

import com.crud.project.model.Developer;
import com.crud.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Developer findByUser(User user);
}

