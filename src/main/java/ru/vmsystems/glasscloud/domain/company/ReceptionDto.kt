package ru.vmsystems.glasscloud.domain.company

import java.util.*

data class ReceptionDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean = false,
        val orderNumPrefix: String,
        val description: String?,
        val address: String,
        val phone: String,
        val companyId: UUID?
)
