package ru.vmsystems.glasscloud.domain.order


import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<OrderEntity, UUID> {
    fun getById(id: UUID): OrderEntity?
}

interface OrderItemRepository : CrudRepository<OrderItemEntity, UUID> {
    fun getByOrderId(orderId: UUID): List<OrderItemEntity>
    fun getById(id: UUID): OrderItemEntity?
}
