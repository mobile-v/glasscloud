package ru.vmsystems.template.domain.shared;

public enum Role {

    ROLE_USER("ROLE_USER", "Пользователь"),
    ROLE_DEVICE("ROLE_DEVICE", "Устройство"),
    ROLE_ADMIN("ROLE_ADMIN", "Администратор");

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
