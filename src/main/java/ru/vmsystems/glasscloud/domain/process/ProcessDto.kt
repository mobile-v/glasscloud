package ru.vmsystems.glasscloud.domain.process

import ru.vmsystems.glasscloud.domain.material.MaterialDto
import java.math.BigDecimal
import java.util.*

data class ProcessTypeDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val description: String = "",
        val companyId: UUID
)

data class ProcessDto(
        var id: UUID? = null,
        val deleted: Boolean,
        val description: String = "",
        val depth: Float,
        val price: BigDecimal,
        val type: ProcessTypeDto,
        val companyId: UUID,
        val material: List<MaterialDto>
)

