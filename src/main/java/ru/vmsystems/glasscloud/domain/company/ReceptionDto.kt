package ru.vmsystems.glasscloud.domain.company

import java.util.*
import javax.persistence.Id

data class ReceptionDto(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val orderNumPrefix: String,
        val description: String,
        val address: String,
        val phone: String,
        val companyId: UUID
)
