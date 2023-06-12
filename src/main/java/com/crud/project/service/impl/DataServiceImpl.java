package com.crud.project.service.impl;

import com.crud.project.model.Broker;
import com.crud.project.model.Developer;
import com.crud.project.model.Image;
import com.crud.project.model.User;
import com.crud.project.repository.BrokerRepository;
import com.crud.project.repository.DeveloperRepository;
import com.crud.project.repository.UserRepository;
import com.crud.project.request.BrokerRequest;
import com.crud.project.request.DeveloperRequest;
import com.crud.project.service.DataService;
import com.crud.project.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        return auth.getName();
    }

    @Override
    public void addBroker(BrokerRequest brokerRequest) {
        Broker broker = new Broker();
        broker.setName(brokerRequest.getName());
        broker.setDescription(brokerRequest.getDescription());
        broker.setPhone(brokerRequest.getPhone());
        broker.setLocation(broker.getLocation());
        broker.setUser(null);
        brokerRepository.save(broker);
    }

    @Override
    public void updateBroker(long id, BrokerRequest brokerRequest) throws IOException {
        if (brokerRepository.findById(id).isPresent()) {
            Broker newBroker = brokerRepository.findById(id).get();
            newBroker.setName(brokerRequest.getName());
            newBroker.setDescription(brokerRequest.getDescription());
            newBroker.setPhone(brokerRequest.getPhone());
            newBroker.setLocation(brokerRequest.getLocation());
            brokerRepository.save(newBroker);
        }
    }

    @Override
    public void deleteBroker(long id) {
        brokerRepository.deleteById(id);
    }

    @Override
    public List<Broker> getBrokers() {
        return brokerRepository.findAll();
    }

    @Override
    public Broker getBrokerById(long id) {
        if (brokerRepository.findById(id).isPresent()) {
            return brokerRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void addDeveloper(DeveloperRequest developerRequest) {
        Developer developer = new Developer();
        developer.setName(developerRequest.getName());
        developer.setLocation(developer.getLocation());
        developer.setFee(developerRequest.getFee());
        developer.setUser(null);
        developerRepository.save(developer);
    }

    @Override
    public void updateDeveloper(long id, DeveloperRequest developerRequest) throws IOException {
        if (developerRepository.findById(id).isPresent()) {
            Developer developer = developerRepository.findById(id).get();
            developer.setName(developerRequest.getName());
            developer.setLocation(developerRequest.getLocation());
            developer.setFee(developerRequest.getFee());
            developerRepository.save(developer);
        }
    }

    @Override
    public Developer getDeveloperById(long id) {
        if (developerRepository.findById(id).isPresent()) {
            return developerRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void deleteDeveloper(long id) {
        developerRepository.deleteById(id);
    }

    @Override
    public List<Developer> getDevelopers() {
        return developerRepository.findAll();
    }
}
