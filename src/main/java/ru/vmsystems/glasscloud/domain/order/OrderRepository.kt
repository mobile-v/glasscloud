package ru.vmsystems.glasscloud.domain.order


import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<OrderEntity, Long>

interface OrderItemRepository : CrudRepository<OrderItemEntity, Long> {
    fun getByOrderId(orderId: Long?): List<OrderItemEntity>
}
