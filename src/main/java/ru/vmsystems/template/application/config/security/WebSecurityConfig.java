package ru.vmsystems.template.application.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.vmsystems.template.domain.shared.Role;

//http://spring-projects.ru/guides/securing-web/

/**
 * @author Vladimir Minikh
 *         Created on 24.04.2015.
 * @EnableWebSecurity подключает необходимые бины для использования Spring Security.
 * Вам также нужен LDAP сервер. Spring Security LDAP модуль подключает встроенный сервер,
 * написанный на чистой Java.
 * Метод ldapAuthentication()
 * настроен так, что имя пользователя из формы входа помещается в {0},
 * таким образом осуществляется поиск uid={0},ou=people,dc=springframework,dc=org на LDAP сервере.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
//                .antMatchers("/api/**").permitAll()

                .antMatchers("/swagger-ui.html").hasAnyAuthority(Role.ROLE_SUPER_ADMIN.toString())
                .antMatchers("/v2/**").hasAnyAuthority(Role.ROLE_SUPER_ADMIN.toString())

                .antMatchers("/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.toString())

                .antMatchers("/").hasAnyAuthority(Role.ROLE_SUPER_ADMIN.toString(), Role.ROLE_ADMIN.toString(), Role.ROLE_USER.toString())
                .antMatchers("/api/**").hasAnyAuthority(Role.ROLE_SUPER_ADMIN.toString(), Role.ROLE_ADMIN.toString(), Role.ROLE_USER.toString())
                .antMatchers("/glass/**").hasAnyAuthority(Role.ROLE_SUPER_ADMIN.toString(), Role.ROLE_ADMIN.toString(), Role.ROLE_USER.toString())

                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/", false)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .httpBasic();
    }

    @Bean
    public BasePasswordEncoder getPasswordEncoder() {
        return new Md5PasswordEncoder();
    }

}
