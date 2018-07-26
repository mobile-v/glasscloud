package ru.vmsystems.template.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.infrastructure.persistence.UserMobuleRepository;

import java.util.List;

@Service
public class UserModuleService {
    private static Logger log = LoggerFactory.getLogger(UserModuleService.class);

    @Autowired
    private UserMobuleRepository userMobuleRepository;

    public List getModules() {
        userMobuleRepository.findAll();
    }
}
