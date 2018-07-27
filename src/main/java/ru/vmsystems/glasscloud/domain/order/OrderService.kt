package ru.vmsystems.glasscloud.domain.order

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.SessionService
import java.util.*

@Service
class OrderService(private val orderRepository: OrderRepository,
                   private val orderItemRepository: OrderItemRepository,
                   private val sessionService: SessionService) {

    //todo сделать проверку на принадлежность заказа пользователю
    fun orders(): List<OrderDto> {
        val currentReceptionId = sessionService.currentReceptionId
        return orderRepository.getByReceptionId(currentReceptionId)
                .map { it.transform() }
    }

    fun getOrdersByReception(receptionId: UUID): List<OrderDto> {
        return orderRepository.getByReceptionId(receptionId)
                .map { it.transform() }
    }

    fun getOrder(orderId: UUID): OrderDto {
        val order = orderRepository.getById(orderId) ?: throw RuntimeException("Заказ не найден")

        return getFullOrder(order)
    }

    private fun getFullOrder(order: OrderEntity): OrderDto {
        val items = orderItemRepository.getByOrderId(order.id!!)
                .map { it.transform() }

        val result = order.transform()

        return result.copy(items = items)
    }

    fun newOrder(order: OrderDto): OrderDto {

        val reception = sessionService.currentReception

        val newOrder = order.copy(
                receptionId = reception.id!!,
                //todo
                number = reception.orderNumPrefix + "1"
//        order . discountSum = "0"
//        order.count = 0
//        order.summa = "0"
//        order.area = 0.0
//        order.perimeter = 0.0
        )



        return updateOrder(order)
    }

    fun updateOrder(order: OrderDto): OrderDto {
        var entity = order.transform()

//        if (entity.items != null) {
//            for (item in entity.items) {
//                item.order = entity
//            }
//        }

        entity = orderRepository.save(entity)

        return entity.transform()
    }

    fun deleteOrder(id: UUID) {
        orderRepository.getById(id)?.let {
            val entity = it.copy(deleted = true)
            orderRepository.save(entity)
        }
    }

    fun getOrderItems(orderId: UUID): List<OrderItemDto> {
        return orderItemRepository.getByOrderId(orderId)
                .map { it.transform() }
    }

    fun getOrderItem(itemId: UUID): OrderItemDto {
        val item = orderItemRepository.getById(itemId) ?: throw RuntimeException("Элемент заказа не найден")
        return item.transform()
    }

    fun saveOrderItem(orderId: UUID, orderItem: OrderItemDto): OrderDto {
        val order = orderRepository.getById(orderId) ?: throw RuntimeException("Заказ не найден")

        val itemEntity = orderItem.transform()

        orderItemRepository.save(itemEntity)
        return getFullOrder(order)
    }

    fun updateOrderItem(itemId: UUID, orderItem: OrderItemDto): OrderItemDto {
        val itemEntity = orderItem.transform(itemId)

        val entity = orderItemRepository.save(itemEntity)
        return entity.transform()
    }

    fun deleteOrderItem(id: UUID) {
        orderItemRepository.getById(id)?.let {
            val entity = it.copy(deleted = true)
            orderItemRepository.save(entity)
        }
    }
}

private fun OrderItemDto.transform(itemId: UUID? = null): OrderItemEntity {
    return OrderItemEntity(
            id = itemId ?: id,
            name = name,
            deleted = deleted,
            creationDate = creationDate,
            lastUpdated = lastUpdated,
            number = number,
            description = description,
            length = length,
            width = width,
            count = count,
            area = area,
            perimeter = perimeter,
            processSum = processSum,
            summa = summa,
            orderId = orderId,
            materialId = materialId
    )
}

private fun OrderDto.transform(): OrderEntity {
    return OrderEntity(
            id = id,
            name = name,
            deleted = deleted,
            creationDate = creationDate,
            lastUpdated = lastUpdated,
            number = number,
            description = description,
            accountNumber = accountNumber,
            discount = discount,
            discountSum = discountSum,
            count = count,
            summa = summa,
            area = area,
            perimeter = perimeter,
            clientId = clientId,
            receptionId = receptionId
    )
}

private fun OrderItemEntity.transform(): OrderItemDto {
    return OrderItemDto(
            id = id,
            name = name,
            deleted = deleted,
            creationDate = creationDate,
            lastUpdated = lastUpdated,
            number = number,
            description = description,
            length = length,
            width = width,
            count = count,
            area = area,
            perimeter = perimeter,
            processSum = processSum,
            summa = summa,
            orderId = orderId,
            materialId = materialId
    )
}

private fun OrderEntity.transform(): OrderDto {
    return OrderDto(
            id = id,
            name = name,
            deleted = deleted,
            creationDate = creationDate,
            lastUpdated = lastUpdated,
            number = number,
            description = description,
            accountNumber = accountNumber,
            discount = discount,
            discountSum = discountSum,
            count = count,
            summa = summa,
            area = area,
            perimeter = perimeter,
            clientId = clientId,
            receptionId = receptionId
    )
}
