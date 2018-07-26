package ru.vmsystems.glasscloud.config.security

enum class Role(private val role: String, val comment: String) {

    ROLE_USER("ROLE_USER", "Пользователь"),
    ROLE_ADMIN("ROLE_ADMIN", "Администратор");

    companion object {
        fun name(role: Role): String {
            return role.role
        }
    }
}
