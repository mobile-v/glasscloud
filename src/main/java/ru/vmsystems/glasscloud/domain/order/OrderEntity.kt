package ru.vmsystems.glasscloud.domain.order

import ru.vmsystems.glasscloud.Util.Companion.createCurrentTime
import ru.vmsystems.glasscloud.domain.material.MaterialEntity
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "order", schema = "glass")
data class OrderEntity(
        @Id
        var id: UUID? = null,
        val deleted: Boolean = false,


        val number: String,
        val description: String?,
        val accountNumber: String = "",
        val clientId: UUID,
        val receptionId: UUID,
        val discount: Float,

        val discountSum: BigDecimal? = null,
        val count: Int? = null,
        val summa: BigDecimal? = null,
        val area: Float? = null,
        val perimeter: Float? = null,
        var creationDate: Long? = null,
        var lastUpdated: Long? = null

) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
        creationDate = createCurrentTime()
        lastUpdated = creationDate
    }

    @PreUpdate
    private fun preUpdate() {
        lastUpdated = createCurrentTime()
    }
}

@Entity
@Table(name = "order_item", schema = "glass")
data class OrderItemEntity(
        @Id
        var id: UUID? = null,
        val deleted: Boolean = false,

        var creationDate: Long,
        var lastUpdated: Long,
        val number: String,
        val description: String?,

        val length: Float,
        val width: Float,
        val count: Int,
        val area: Float,
        val perimeter: Float,
        val processSum: BigDecimal,
        val summa: BigDecimal,
        val orderId: UUID,
//        val materialId: UUID,
        @ManyToOne
        val material: MaterialEntity
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
        creationDate = createCurrentTime()
    }

    @PreUpdate
    private fun preUpdate() {
        lastUpdated = createCurrentTime()
    }
}
