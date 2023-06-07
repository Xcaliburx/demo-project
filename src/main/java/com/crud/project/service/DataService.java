package com.crud.project.service;

import com.crud.project.model.Broker;
import com.crud.project.model.Developer;
import com.crud.project.request.BrokerRequest;
import com.crud.project.request.DeveloperRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DataService {
    void addBroker(BrokerRequest brokerRequest);

    void updateBroker(long id, BrokerRequest brokerRequest) throws IOException;

    void deleteBroker(long id);

    List<Broker> getBrokers();

    Broker getBrokerById(long id);

    void addDeveloper(DeveloperRequest developer);

    void updateDeveloper(long id, DeveloperRequest developerRequest) throws IOException;

    Developer getDeveloperById(long id);

    void deleteDeveloper(long id);

    List<Developer> getDevelopers();
}
