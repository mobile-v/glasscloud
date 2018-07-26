package ru.vmsystems.glasscloud.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

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
@EnableGlobalMethodSecurity(securedEnabled = true)
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

        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .cors();

        http.authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers("/openapi/**").permitAll()
                .antMatchers("/openapi/**").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
                .antMatchers("/api/**").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())

                .and()
                .formLogin()
                .failureHandler((request, response, exception) -> response.setStatus(SC_UNAUTHORIZED))
                .successHandler((request, response, authentication) -> response.setStatus(SC_OK))
                .loginPage("/login")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(SC_OK))
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

}
