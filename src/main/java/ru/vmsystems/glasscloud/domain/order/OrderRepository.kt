package ru.vmsystems.glasscloud.domain.order


import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<OrderEntity, UUID> {
    fun getById(id: UUID): OrderEntity?
    fun getByReceptionId(receptionId: UUID): List<OrderEntity>
}

interface OrderItemRepository : CrudRepository<OrderItemEntity, UUID> {
    @Query("from OrderItemEntity s where s.deleted = false")
    fun getByOrderId(orderId: UUID): List<OrderItemEntity>
    fun getById(id: UUID): OrderItemEntity?
}
