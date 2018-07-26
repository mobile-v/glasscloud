package ru.vmsystems.glasscloud.domain.order

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "order", schema = "glass")
data class OrderEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,

        val creationDate: Long,
        val lastUpdated: Long,
        val number: String,
        val description: String = "",
        val accountNumber: String = "",
        val discount: Float,
        val discountSum: BigDecimal,
        val count: Int,
        val summa: BigDecimal,
        val area: Float,
        val perimeter: Float,
        val clientId: UUID,
        val receptionOfOrderId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}

@Entity
@Table(name = "order_item", schema = "glass")
data class OrderItemEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,

        val creationDate: Long,
        val lastUpdated: Long,
        val number: String,
        val description: String = "",

        val length: Float,
        val width: Float,
        val count: Int,
        val area: Float,
        val perimeter: Float,
        val processSum: BigDecimal,
        val summa: BigDecimal,
        val orderId: UUID,
        val materialId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
