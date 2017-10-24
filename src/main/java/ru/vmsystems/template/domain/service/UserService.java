package ru.vmsystems.template.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.UserData;

import java.util.Optional;

@Service
public class UserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    public UserData getById(Integer id) {
        Optional<UserEntity> user = userRepository.getById(id);
        if (user.isPresent()) {
            return UserFactory.create(userRepository.getById(id).get());
        } else {
            return new UserData();
        }
    }


    public String editUser(UserData userData) {
        String result = null;
        Optional<UserEntity> userDb = userRepository.getByLogin(userData.getLogin());
        log.info("Редактировать данные пользователя: " + userData.getLogin());
        if (userDb.isPresent()) {
            if (!userDb.get().getName().equals(userData.getName())) {
                userDb.get().setName(userData.getName());
            }
            if (!userDb.get().getRole().equals(userData.getRole().toString())) {
                userDb.get().setRole(userData.getRole().toString());
            }

            String masterPassword = new Md5PasswordEncoder().encodePassword(userData.getPassword(), null);
            if (!userData.getPassword().equals("") && !masterPassword.equals(userData.getRole().toString())) {
                if (userData.getPassword().length() >= 8) {
                    userDb.get().setRole(userData.getRole().toString());
                } else {
                    String message = String.format(
                            "Данные пользователя c логином <b> %s </b> не изменены. </br> Короткий пароль", userData.getLogin());
                }
            }

            userRepository.save(userDb.get());

            String message = String.format("Данные пользователя c логином <b> %s </b> успешно изменены. </br> ", userData.getLogin());
        } else

        {
            log.warn("Пользователь " + userDb.get().getLogin() + " не существует");
            String message = String.format("Пользователя c логином <b> %s </b> не существует. " +
                    "Закройте эту страницу, введите правильный логин и попробуйте еще раз.", userData.getLogin());
        }

        return result;
    }

    public String regNewUser(UserData userData) {
        String result = null;
        Optional<UserEntity> userDb = userRepository.getByLogin(userData.getLogin());
        log.info("Создать нового пользователя: " + userData.getLogin());
        if (!userDb.isPresent()) {
            //новый пользователь
            UserEntity user = UserFactory.create(userData);
            userRepository.save(user);

            String message = String.format("Пользователь c логином <b> %s </b> создан. </br> ", userData.getLogin());
        } else {
            log.warn("Пользователь " + userDb.get().getLogin() + " уже существует");
            String message = String.format("Пользователь c логином <b> %s </b> уже существует. " +
                    "Закройте эту страницу, введите новый логин и попробуйте еще раз.", userData.getLogin());
        }

        return result;
    }
}
