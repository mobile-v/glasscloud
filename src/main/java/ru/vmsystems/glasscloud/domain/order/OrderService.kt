package ru.vmsystems.glasscloud.domain.order

import com.google.common.collect.Lists
import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.company.ReceptionRepository
import java.sql.Timestamp
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class OrderService(private val orderRepository: OrderRepository,
                   private val orderItemRepository: OrderItemRepository,
                   private val receptionOfOrderRepository: ReceptionRepository,
                   private val clientRepository: ClientRepository,
                   private val materialRepository: MaterialRepository,
                   private val processRepository: ProcessRepository,
                   private val sessionService: SessionService) {

    //todo сделать проверку на принадлежность заказа пользователю
    val orders: List<OrderDto>
        get() = StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map { order -> mapper.map(order, OrderDto::class.java) }
                .collect(Collectors.toList())

    fun getOrdersByReceptionOfOrder(receptionOfOrder: Long?): List<OrderDto> {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .filter { order -> receptionOfOrder == order.receptionOfOrder.id }
                .map { order -> mapper.map(order, OrderDto::class.java) }
                .collect(Collectors.toList())
    }

    fun getOrder(orderId: Long): Optional<OrderDto> {
        val order = orderRepository.findOne(orderId) ?: return Optional.empty()

        val result = getFullOrder(order)

        return Optional.of(result)
    }

    private fun getFullOrder(order: OrderEntity): OrderDto {
        val result = mapper.map(order, OrderDto::class.java)

        val items = Lists.newArrayList<OrderItemDto>()
        orderItemRepository.getByOrderId(order.id)
                .forEach { item -> items.add(mapper.map(item, OrderItemDto::class.java)) }
        result.items = items

        return result
    }

    fun newOrder(order: OrderDto): OrderDto {

        if (!sessionService.currentReceptionOfOrder.isPresent) {
            return order
        }

        val receptionOfOrder = sessionService.currentReceptionOfOrder.get()

        //todo
        order.receptionOfOrder = mapper.map(receptionOfOrder, ReceptionOfOrderDto::class.java)
        order.number = receptionOfOrder.orderNumPrefix + "1"
        if (order.discount == null) {
            order.discount = "0"
        }
        order.creationDate = Timestamp(Date().time).toString()
        order.discountSum = "0"
        order.count = 0
        order.summa = "0"
        order.area = 0.0
        order.perimeter = 0.0

        return updateOrder(order)
    }

    fun updateOrder(order: OrderDto): OrderDto {
        val client = clientRepository.findOne(order.client.id)

        var entity = mapper.map(order, OrderEntity::class.java)
        val receptionOfOrder = receptionOfOrderRepository.findOne(entity.receptionOfOrder.id)

        entity.client = client
        entity.receptionOfOrder = receptionOfOrder
        if (entity.creationDate == null) {
            entity.creationDate = Timestamp(Date().time)
        }
        entity.updateDate = Timestamp(Date().time)
        if (entity.items != null) {
            for (item in entity.items) {
                if (item.creationDate == null) {
                    item.creationDate = Timestamp(Date().time)
                }
                item.updateDate = Timestamp(Date().time)
                item.order = entity
            }
        }

        entity = orderRepository.save(entity)

        return mapper.map(entity, OrderDto::class.java)
    }

    fun deleteOrder(orderId: Long?) {
        orderRepository.delete(orderId)
    }

    fun getOrderItems(orderId: Long?): List<OrderItemDto> {
        val result = Lists.newArrayList<OrderItemDto>()
        orderItemRepository.getByOrderId(orderId)
                .forEach { item -> result.add(mapper.map(item, OrderItemDto::class.java)) }
        return result
    }

    fun getOrderItem(itemId: Long?): OrderItemDto {
        val item = orderItemRepository.findOne(itemId)
        return mapper.map(item, OrderItemDto::class.java)
    }

    fun saveOrderItem(orderId: Long?, orderItem: OrderItemDto): OrderDto {
        val order = orderRepository.findOne(orderId)

        val material = materialRepository.findOne(orderItem.material.id)
        val itemEntity = mapper.map(orderItem, OrderItemEntity::class.java)
        itemEntity.material = material
        itemEntity.order = order
        itemEntity.creationDate = Timestamp(Date().time)
        itemEntity.updateDate = Timestamp(Date().time)

        order.items.add(itemEntity)

        val entity = orderRepository.save(order)
        return getFullOrder(entity)
    }

    fun updateOrderItem(orderId: Long?, orderItem: OrderItemDto): OrderItemDto {
        val orderItemDb = orderItemRepository.findOne(orderId)

        val processes = orderItem.process.stream()
                .map { item -> processRepository.findOne(item.id) }
                .collect<List<ProcessEntity>, Any>(Collectors.toList())

        val itemEntity = mapper.map(orderItem, OrderItemEntity::class.java)
        itemEntity.material = itemEntity.material
        itemEntity.process = processes
        itemEntity.order = orderItemDb.order
        itemEntity.creationDate = Timestamp(Date().time)
        itemEntity.updateDate = Timestamp(Date().time)

        val entity = orderItemRepository.save(itemEntity)
        return mapper.map(entity, OrderItemDto::class.java)
    }

    fun deleteOrderItem(itemId: Long?) {
        orderItemRepository.delete(itemId)
    }
}
