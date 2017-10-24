package ru.vmsystems.template.interfaces.dto;

import org.hibernate.validator.constraints.Email;
import ru.vmsystems.template.domain.shared.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("NNInspection")
public final class UserData {
    private Long id;
    @NotNull
    @Size(min=2, max = 20, message = "Имя должно содержать от 8 до 20 символов")
    private String name;
    @NotNull
    private String login;
    @NotNull(message = "Пароль не может быть пустым")
    @Size(min=8, max = 20, message = "Пароль должен содержать от 8 до 20 символов")
    private String password;
    @Email(message = "Неправильный формат email")
    private String email;
    private String phone;
    private String comment;
    private boolean enabled;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", enabled=" + enabled +
                ", role=" + role +
                '}';
    }
}
