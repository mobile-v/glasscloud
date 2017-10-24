package ru.vmsystems.template.application.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Краткое описание</p>
 * <p>Подробное описание</p>
 *
 * @author Vladimir Minikh
 *         Created on 26.05.2015.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        // с помощью нашего сервиса UserService получаем User
        Optional<UserEntity> user = userRepository.getByLogin(login);
        if (!user.isPresent()) throw new UsernameNotFoundException("Пользователь не найден");

        // указываем роли для этого пользователя
        Set<GrantedAuthority> roles = new HashSet<>();

        roles.add(new SimpleGrantedAuthority(user.get().getRole()));

        // на основании полученныйх даных формируем объект UserDetails
        // который позволит проверить введеный пользователем логин и пароль
        // и уже потом аутентифицировать пользователя
        return new User(
                user.get().getLogin(),
                user.get().getPassword(),
                user.get().isEnabled(),
                true, true, true,
                roles);
    }
}
