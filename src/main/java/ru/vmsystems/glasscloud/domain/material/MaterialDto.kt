package ru.vmsystems.glasscloud.domain.material

import java.math.BigDecimal
import java.util.*

data class MaterialDto(
        var id: UUID?,
        val deleted: Boolean = false,
        val depth: Float,
        val length: Float,
        val width: Float,
        val price: BigDecimal,
        val description: String?,
        val color: MaterialColorDto,
        val type: MaterialTypeDto,
        val companyId: UUID?
)

data class MaterialColorDto(
        var id: UUID?,
        val name: String,
        val deleted: Boolean = false,
        val companyId: UUID?
) {
//    constructor(id: String, name: String, deleted: Boolean, companyId: String) : this(
//            id = UUID.fromString(id),
//            name = name,
//            deleted = deleted,
//            companyId = UUID.fromString(companyId)
//    )
}

data class MaterialTypeDto(
        var id: UUID?,
        val name: String,
        val deleted: Boolean = false,
        val companyId: UUID?
)