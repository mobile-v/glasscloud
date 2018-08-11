package ru.vmsystems.glasscloud.domain.order

import ru.vmsystems.glasscloud.domain.material.MaterialDto
import java.math.BigDecimal
import java.util.*

data class OrderCreateDto(
        val clientId: UUID,
        val discount: Float,
        val description: String? = null
)

data class OrderDto(
        var id: UUID? = null,
        val deleted: Boolean = false,
        val creationDate: Long,
        val lastUpdated: Long,
        val number: String,
        val description: String? = null,
        val accountNumber: String,
        val discount: Float,
        val discountSum: BigDecimal,
        val count: Int,
        val summa: BigDecimal,
        val area: Float,
        val perimeter: Float,
        val clientId: UUID,
        val clientName: String? = null,
        val receptionId: UUID?,
        val reception: String? = null,

        val items: List<OrderItemDto>? = null
)

data class OrderItemDto(
        var id: UUID? = null,
        val deleted: Boolean = false,
        val creationDate: Long,
        val lastUpdated: Long,
        val number: String,
        val description: String? = null,
        val length: Float,
        val width: Float,
        val count: Int,
        val area: Float,
        val perimeter: Float,
        val processSum: BigDecimal,
        val summa: BigDecimal,
        val orderId: UUID,
//        val materialId: UUID,
        val material: MaterialDto
)
