package ru.vmsystems.glasscloud.domain.order

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.client.ClientRepository
import ru.vmsystems.glasscloud.domain.material.transform
import ru.vmsystems.glasscloud.domain.process.transform
import ru.vmsystems.glasscloud.handler.BusinessException
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

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
                .map {
                    it.transform(reception = currentReception.name,
                            clientName = clientRepository.getById(it.clientId)!!.name)
                }
    }

    fun getOrdersByReception(receptionId: UUID): List<OrderDto> {
        return orderRepository.getByReceptionId(receptionId)
                .map {
                    it.transform(reception = sessionService.currentReception.name,
                            clientName = clientRepository.getById(it.clientId)!!.name)
                }
    }

    fun getOrder(orderId: UUID): OrderDto {
        val order = orderRepository.getById(orderId) ?: throw RuntimeException("Заказ не найден")

        return getFullOrder(order)
    }

    private fun getFullOrder(order: OrderEntity): OrderDto {
        val items = orderItemRepository.getByOrderId(order.id!!)
                .map { it.transform() }

        val result = order
                .transform(reception = sessionService.currentReception.name,
                        clientName = clientRepository.getById(order.clientId)!!.name)

        return result.copy(items = items)
    }

    @Transactional
    fun newOrder(order: OrderCreateDto): OrderDto {

        val reception = sessionService.currentReception
        val entity = order
                .transform(sessionService.currentReceptionId, reception.orderNumPrefix + "1")

        val res = orderRepository.save(entity)

        return updateOrder(res.transform(reception = sessionService.currentReception.name,
                clientName = clientRepository.getById(res.clientId)!!.name))
    }

    @Transactional
    fun updateOrder(order: OrderDto): OrderDto {
        val calculatedOrder = order
                .calculate()

        val entity = calculatedOrder.transform(sessionService.currentReceptionId)
        val items = calculatedOrder.items
                ?.map { it.transform() }

        val savedEntity = orderRepository.save(entity)
        val orderDb = savedEntity.transform(reception = sessionService.currentReception.name,
                clientName = clientRepository.getById(savedEntity.clientId)!!.name)
        if (items != null) {
            val itemsDb = orderItemRepository.saveAll(items)
                    .map { it.transform() }
            return orderDb.copy(items = itemsDb)
        }

        return orderDb
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
        val item = orderItemRepository.getById(itemId)
                ?: throw RuntimeException("Элемент заказа не найден")
        return item.transform()
    }

    @Transactional
    fun saveOrderItem(orderId: UUID, orderItem: OrderItemDto): OrderDto {
        val order = orderRepository.getById(orderId) ?: throw RuntimeException("Заказ не найден")

        val itemEntity = orderItem.calculate().transform()

        orderItemRepository.save(itemEntity)
        val o = calculateOrder(order.transform())

        orderRepository.save(o.transform())

        return o
    }

    fun updateOrderItem(itemId: UUID, orderItem: OrderItemDto): OrderItemDto {
        calculateOrder(orderItem.orderId)
        val itemEntity = orderItem.transform(itemId)

        val entity = orderItemRepository.save(itemEntity)
        return entity.transform()
    }

    fun deleteOrderItem(id: UUID) {
        orderItemRepository.getById(id)?.let {
            val entity = it.copy(deleted = true)
            orderItemRepository.save(entity)
            calculateOrder(entity.orderId)
        }
    }

    fun calculateOrder(order: OrderDto): OrderDto {
        return order.calculate()
    }

    fun calculateOrder(orderId: UUID): OrderDto {
        val order = orderRepository.getById(orderId) ?: throw BusinessException("Заказ не найден")
        return calculateOrder(getFullOrder(order))
    }
}

private fun OrderDto.calculate(): OrderDto {
    val orderItems = items
            ?.map { it.calculate() }

    val orderSum = if (orderItems == null || orderItems.isEmpty()) {
        BigDecimal.ZERO
    } else {
        orderItems
                .map { it.summa }
                .reduce { acc, bigDecimal -> acc.add(bigDecimal) }
    }

    return copy(
            summa = orderSum,
            discountSum = orderSum - (orderSum * BigDecimal(discount / 100.0)),
            items = orderItems
    )
}

private fun OrderItemDto.calculate(): OrderItemDto {
    val perimeter = (length * width) * 2.0 * (count / 1_000.0)
    var area = (length * width) / 1_000_000.0

    area = if (area < (0.0625 * count)) {
        0.0625 * count
    } else {
        area * count
    }

    val processSum = if (process == null || process.isEmpty()) {
        BigDecimal.ZERO
    } else {
        process
                .map { it.price.multiply(BigDecimal(perimeter)) }
                .reduce { acc, bigDecimal -> acc.add(bigDecimal) }
    }

    val sum = BigDecimal(area) * material.price + processSum

    return copy(
            area = area.toFloat(),
            perimeter = perimeter.toFloat(),
            processSum = processSum,
            summa = sum
    )
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
//            materialId = materialId,
            material = material.transform(),
            process = process?.map { it.transform() }
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
//            materialId = materialId,
            material = material.transform(),
            process = process
                    ?.map { it.transform() }
    )
}

//private fun ProcessEntity.transform(): ProcessDto {
//    return ProcessDto(
//            id = id,
//                    deleted = deleted,
//                    description = description,
//                    depth = depth,
//                    price = price,
//                    type = type.transform(),
//                    companyId = companyId,
//                    material = material.map { it.transform() }
//    )
//}

private fun OrderEntity.transform(clientName: String? = null, reception: String? = null): OrderDto {
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
