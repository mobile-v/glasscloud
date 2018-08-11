package ru.vmsystems.glasscloud.domain.order

import java.math.BigDecimal
import java.util.*

data class OrderDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean = false,
        val creationDate: Long,
        val lastUpdated: Long,
        val number: String,
        val description: String,
        val accountNumber: String,
        val discount: Float,
        val discountSum: BigDecimal,
        val count: Int,
        val summa: BigDecimal,
        val area: Float,
        val perimeter: Float,
        val clientId: UUID,
        val receptionId: UUID?,

        val items: List<OrderItemDto>? = null
)

data class OrderItemDto(
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean = false,
        val creationDate: Long,
        val lastUpdated: Long,
        val number: String,
        val description: String,
        val length: Float,
        val width: Float,
        val count: Int,
        val area: Float,
        val perimeter: Float,
        val processSum: BigDecimal,
        val summa: BigDecimal,
        val orderId: UUID,
        val materialId: UUID
)
