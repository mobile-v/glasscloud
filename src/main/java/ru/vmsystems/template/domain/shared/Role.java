package ru.vmsystems.template.domain.shared;

public enum Role {

    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", "Супер админ"),
    ROLE_ADMIN("ROLE_ADMIN", "Администратор"),
    ROLE_USER("ROLE_USER", "Пользователь");

    private String role;
    private String comment;

    Role(String role, String comment) {
        this.role = role;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return role;
    }

    public String getComment() {
        return comment;
    }
}
