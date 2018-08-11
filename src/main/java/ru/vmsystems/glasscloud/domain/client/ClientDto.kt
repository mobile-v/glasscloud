package ru.vmsystems.glasscloud.domain.client

import java.util.*

class ClientTypeDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean = false
)

class ClientDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean = false,
        val inn: Long,
        val account: String,
        val phone: String,
        val email: String,
        val description: String = "",
        val discount: Float,
        val type: ClientTypeDto,
        val companyId: UUID?
)
