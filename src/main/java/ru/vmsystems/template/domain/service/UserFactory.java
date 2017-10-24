package ru.vmsystems.template.domain.service;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.shared.Role;
import ru.vmsystems.template.interfaces.dto.UserData;

class UserFactory {
    public static UserEntity create(UserData userData){

        UserEntity user;
        user = new UserEntity();
        user.setId(userData.getId());
        user.setName(userData.getName());
        user.setLogin(userData.getLogin());
        String masterPassword = new Md5PasswordEncoder().encodePassword(userData.getPassword(), null);
        user.setPassword(masterPassword);
        user.setEnabled(userData.isEnabled());

        user.setRole(userData.getRole().toString());

        return user;
    }

    public static UserData create(UserEntity userData){

        UserData user;
        user = new UserData();
        user.setId(userData.getId());
        user.setName(userData.getName());
        user.setLogin(userData.getLogin());
        user.setEnabled(userData.isEnabled());

        user.setRole(Role.valueOf(userData.getRole()));

        return user;
    }
}
