package ru.vmsystems.glasscloud.domain.order


import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<OrderEntity, UUID>

interface OrderItemRepository : CrudRepository<OrderItemEntity, UUID> {
    fun getByOrderId(orderId: UUID): List<OrderItemEntity>
}
