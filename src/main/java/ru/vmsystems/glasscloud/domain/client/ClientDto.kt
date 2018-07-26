package ru.vmsystems.glasscloud.domain.client

import java.util.*

class ClientTypeDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean
)

class ClientDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val inn: String,
        val account: String,
        val phone: String,
        val email: String,
        val description: String = "",
        val discount: String,
        val clientType: ClientTypeEntity,
        val companyId: UUID
)
