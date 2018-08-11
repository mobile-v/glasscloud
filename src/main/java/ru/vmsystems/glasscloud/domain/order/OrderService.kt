package ru.vmsystems.glasscloud.domain.order

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.client.ClientRepository
import java.math.BigDecimal
import java.util.*

@Service
class OrderService(
        private val orderRepository: OrderRepository,
        private val orderItemRepository: OrderItemRepository,
        private val sessionService: SessionService,
        private val clientRepository: ClientRepository
) {

    //todo сделать проверку на принадлежность заказа пользователю
    fun orders(): List<OrderDto> {
        val currentReception = sessionService.currentReception
        return orderRepository.getByReceptionId(currentReception.id!!)
                .map { it.transform(reception = currentReception.name, clientName = clientRepository.getById(it.clientId)!!.name) }
    }

    fun getOrdersByReception(receptionId: UUID): List<OrderDto> {
        return orderRepository.getByReceptionId(receptionId)
                .map { it.transform(reception = sessionService.currentReception.name, clientName = clientRepository.getById(it.clientId)!!.name) }
    }

    fun getOrder(orderId: UUID): OrderDto {
        val order = orderRepository.getById(orderId) ?: throw RuntimeException("Заказ не найден")

        return getFullOrder(order)
    }

    private fun getFullOrder(order: OrderEntity): OrderDto {
        val items = orderItemRepository.getByOrderId(order.id!!)
                .map { it.transform() }

        val result = order.transform(reception = sessionService.currentReception.name, clientName = clientRepository.getById(order.clientId)!!.name)

        return result.copy(items = items)
    }

    fun newOrder(order: OrderCreateDto): OrderDto {

        val reception = sessionService.currentReception
        val entity = order.transform(sessionService.currentReceptionId,
                reception.orderNumPrefix + "1")

        val res = orderRepository.save(entity)

        return updateOrder(res.transform(reception = sessionService.currentReception.name, clientName = clientRepository.getById(res.clientId)!!.name))
    }

    fun updateOrder(order: OrderDto): OrderDto {
        var entity = order.transform(sessionService.currentReceptionId)

//        if (entity.items != null) {
//            for (item in entity.items) {
//                item.order = entity
//            }
//        }

        entity = orderRepository.save(entity)

        return entity.transform(reception = sessionService.currentReception.name, clientName = clientRepository.getById(entity.clientId)!!.name)
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

private fun OrderCreateDto.transform(currentReceptionId: UUID, number: String): OrderEntity {
    return OrderEntity(
            clientId = clientId,
            description = description,
            discount = discount,
            number = number,
            receptionId = currentReceptionId
    )
}

private fun OrderItemDto.transform(itemId: UUID? = null): OrderItemEntity {
    return OrderItemEntity(
            id = itemId ?: id,
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

private fun OrderDto.transform(currentReceptionId: UUID? = null): OrderEntity {
    return OrderEntity(
            id = id,
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
            receptionId = currentReceptionId ?: receptionId!!
    )
}

private fun OrderItemEntity.transform(): OrderItemDto {
    return OrderItemDto(
            id = id,
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

private fun OrderEntity.transform(clientName: String, reception: String): OrderDto {
    return OrderDto(
            id = id,
            deleted = deleted,
            creationDate = creationDate ?: 0,
            lastUpdated = lastUpdated ?: 0,
            number = number,
            description = description,
            accountNumber = accountNumber,
            discount = discount,
            discountSum = discountSum ?: BigDecimal.ZERO,
            count = count ?: 0,
            summa = summa ?: BigDecimal.ZERO,
            area = area ?: 0f,
            perimeter = perimeter ?: 0f,
            clientId = clientId,
            clientName = clientName,
            receptionId = receptionId,
            reception = reception
    )
}
