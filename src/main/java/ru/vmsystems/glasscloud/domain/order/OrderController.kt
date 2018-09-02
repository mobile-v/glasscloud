package ru.vmsystems.glasscloud.domain.order

import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.getLogger
import ru.vmsystems.glasscloud.handler.BusinessException
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import ru.vmsystems.glasscloud.user.UserRepository
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/api")
class OrderController(
        private val orderService: OrderService,
        private val sessionService: SessionService,
        private val userRepository: UserRepository
) {

    @ApiOperation(value = "Рассчитать стоимость заказа")
    @PostMapping(value = ["/order/calculate"])
    fun calculateOrder(@RequestBody order: OrderDto, principal: Principal): JsonItemResponse<OrderDto> {
        userRepository.getByLogin(principal.name) ?: throw BusinessException("Пользователь не найден")
        LOG.info("-- calculate order --")

        val result = orderService.calculateOrder(order)
        LOG.info("{}", result)
        return JsonItemBuilder.success(result)
    }

    @ApiOperation(value = "Получить список всех заказов по точке выдачи")
    @GetMapping(value = ["/order"])
    fun getOrdersByReceptionOfOrder(
            principal: Principal): JsonItemResponse<List<OrderDto>> {
        userRepository.getByLogin(principal.name) ?: throw BusinessException("Пользователь не найден")

        val receptionOfOrder = sessionService.currentReception

        val result = orderService.getOrdersByReception(receptionOfOrder.id!!)
        return JsonItemBuilder.success(result)
    }

    @ApiOperation(value = "Получить заказ по id заказа")
    @GetMapping(value = ["/order/{orderId}"])
    fun getOrder(@PathVariable(value = "orderId") orderId: UUID): JsonItemResponse<OrderDto> {

        val order = orderService.getOrder(orderId)
        return JsonItemBuilder.success(order)
    }

    @ApiOperation(value = "Создать новый заказ")
    @PostMapping(value = ["/order"])
    //    @PreAuthorize("@moduleSecurity.orderPermitted")
    fun newOrder(@RequestBody order: OrderCreateDto, principal: Principal): JsonItemResponse<OrderDto> {
        userRepository.getByLogin(principal.name) ?: throw BusinessException("Пользователь не найден")
        LOG.info("-- save --")

        val res = orderService.newOrder(order)
        return JsonItemBuilder.success(res)
    }

    @ApiOperation(value = "Обновить заказ")
    @PutMapping(value = ["/order/{orderId}"])
    fun updateOrder(@PathVariable(value = "orderId") orderId: UUID,
                    @RequestBody order: OrderDto, principal: Principal): JsonItemResponse<OrderDto> {
        userRepository.getByLogin(principal.name) ?: throw BusinessException("Пользователь не найден")
        LOG.info("-- update --")

        val newOrder = order.copy(id = orderId)
        val result = orderService.updateOrder(newOrder)

        return JsonItemBuilder.success(result)
    }

    @RequestMapping(method = [(RequestMethod.OPTIONS)])
    fun options(): JsonItemResponse<*> {
        LOG.info("-- options --")
        val headers = HttpHeaders()
        headers.add(HttpHeaders.ACCEPT, RequestMethod.POST.name)
        return JsonItemBuilder.success(headers)
    }

    @ApiOperation(value = "Удалить заказ по id заказа")
    @DeleteMapping(value = ["/order/{orderId}"])
    fun deleteOrder(@PathVariable(value = "orderId") orderId: UUID): JsonItemResponse<*> {
        orderService.deleteOrder(orderId)
        return JsonItemBuilder.success()
    }

    @GetMapping(value = ["/order/items/{orderId}"])
    fun getOrderItems(@PathVariable(value = "orderId") orderId: UUID): JsonItemResponse<List<OrderItemDto>> {
        val result = orderService.getOrderItems(orderId)
        return JsonItemBuilder.success(result)
    }

    @PostMapping(value = ["/order/items/{orderId}"])
    fun saveOrderItem(@PathVariable(value = "orderId") orderId: UUID,
                      @RequestBody orderItem: OrderItemDto): JsonItemResponse<OrderDto> {
        val result = orderService.saveOrderItem(orderId, orderItem)
        return JsonItemBuilder.success(result)
    }

    @GetMapping(value = ["/order/item/{itemId}"])
    fun getOrderItem(@PathVariable(value = "itemId") itemId: UUID): JsonItemResponse<OrderItemDto> {
        val result = orderService.getOrderItem(itemId)
        return JsonItemBuilder.success(result)
    }

    @PutMapping(value = ["/order/item/{itemId}"])
    fun updateOrderItem(@PathVariable(value = "itemId") itemId: UUID,
                        @RequestBody orderItemDto: OrderItemDto): JsonItemResponse<OrderItemDto> {
        val result = orderService.updateOrderItem(itemId, orderItemDto)
        return JsonItemBuilder.success(result)
    }

    @DeleteMapping(value = ["/order/item/{itemId}"])
    fun deleteOrderItem(@PathVariable(value = "itemId") itemId: UUID): JsonItemResponse<*> {
        orderService.deleteOrderItem(itemId)
        return JsonItemBuilder.success()
    }

    companion object {
        private val LOG = getLogger()
    }
}
