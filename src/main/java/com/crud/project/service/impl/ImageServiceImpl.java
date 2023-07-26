package com.crud.project.service.impl;

import com.crud.project.model.Broker;
import com.crud.project.model.Developer;
import com.crud.project.model.Image;
import com.crud.project.model.User;
import com.crud.project.repository.BrokerRepository;
import com.crud.project.repository.DeveloperRepository;
import com.crud.project.repository.ImageRepository;
import com.crud.project.repository.UserRepository;
import com.crud.project.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public Image saveImage(MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        System.out.println(user);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Image image = new Image(fileName, file.getContentType(), file.getBytes());

        if (user.getRole().equals("Developer")) {
            Developer developer = developerRepository.findByUser(user);
            developer.setImage(image);
            developerRepository.save(developer);
        } else if (user.getRole().equals("Broker")) {
            Broker broker = brokerRepository.findByUser(user);
            broker.setImage(image);
            brokerRepository.save(broker);
        }
        return repository.save(image);
    }

    @Override
    public Image getImage(String id) {
        return repository.findById(id).get();
    }
}
