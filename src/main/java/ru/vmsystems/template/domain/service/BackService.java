package ru.vmsystems.template.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public abstract class BackService {

    @Autowired
    private HttpServletRequest httpRequest;
    @Autowired
    private UserRepository userRepository;

    public String getSesionId() {
        return httpRequest.getSession().getId();
    }

    public String getLogin() {
        return httpRequest.getRemoteUser();
    }

    public CompanyEntity getCompany() {
        Optional<UserEntity> user = userRepository.getByLogin(getLogin());
        if (user.isPresent()) {
            return user.get().getCompany();
        } else {
            throw new RuntimeException("user not found " + getLogin());
        }
    }

    public Long getCompanyId() {
        return getCompany().getId();
    }
}
