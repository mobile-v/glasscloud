package ru.vmsystems.glasscloud.user

import java.util.*

data class UserDto(
        var id: UUID? = null,
        val name: String,
        val login: String,
        val password: String,
        val enabled: Boolean,
        val role: String,
        val email: String,
        val phone: String,
        val comment: String?,
        val companyId: UUID?,
        val deleted: Boolean = false
)
