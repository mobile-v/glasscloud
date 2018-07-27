package ru.vmsystems.glasscloud.domain.order

import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.getLogger
import ru.vmsystems.glasscloud.user.UserRepository
import java.security.Principal
import java.util.*

@RestController("OrderControllerApi")
@RequestMapping("/api")
class OrderController(private val orderService: OrderService,
            private val sessionService: SessionService,
            private val userRepository: UserRepository) {

    @ApiOperation(value = "Получить список всех заказов по точке выдачи")
    @GetMapping(value = ["/order"])
    fun getOrdersByReceptionOfOrder(
            principal: Principal): ResponseEntity<List<OrderDto>> {

        val user = userRepository.getByLogin(principal.name) ?: return ResponseEntity(HttpStatus.FORBIDDEN)

        val receptionOfOrder = sessionService.currentReception

        val result = orderService.getOrdersByReception(receptionOfOrder.id!!)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @ApiOperation(value = "Получить заказ по id заказа")
    @GetMapping(value = ["/order/{orderId}"])
    fun getOrder(@PathVariable(value = "orderId") orderId: UUID): ResponseEntity<OrderDto> {

        val order = orderService.getOrder(orderId)
        return ResponseEntity(order, HttpStatus.OK)
    }

    @ApiOperation(value = "Создать новый заказ")
    @PostMapping(value = ["/order"])
    //    @PreAuthorize("@moduleSecurity.orderPermitted")
    fun newOrder(@RequestBody order: OrderDto, principal: Principal): ResponseEntity<OrderDto> {
        var order = order
        userRepository.getByLogin(principal.name) ?: return ResponseEntity(HttpStatus.FORBIDDEN)
        LOG.info("-- save --")

        if (order.id != null) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        order = orderService.newOrder(order)
        return ResponseEntity(order, HttpStatus.OK)
    }

    @ApiOperation(value = "Обновить заказ")
    @PutMapping(value = ["/order/{orderId}"])
    fun updateOrder(@PathVariable(value = "orderId") orderId: UUID,
                    @RequestBody order: OrderDto, principal: Principal): ResponseEntity<OrderDto> {
        var order = order
        userRepository.getByLogin(principal.name) ?: return ResponseEntity(HttpStatus.FORBIDDEN)
        LOG.info("-- update --")

        order.id = orderId
        order = orderService.updateOrder(order)

        return ResponseEntity(order, HttpStatus.OK)
    }

    @RequestMapping(method = [(RequestMethod.OPTIONS)])
    fun options(): ResponseEntity<*> {
        LOG.info("-- options --")
        val headers = HttpHeaders()
        headers.add(HttpHeaders.ACCEPT, RequestMethod.POST.name)
        return ResponseEntity<Any>(headers, HttpStatus.OK)
    }

    @ApiOperation(value = "Удалить заказ по id заказа")
    @DeleteMapping(value = ["/order/{orderId}"])
    fun deleteOrder(@PathVariable(value = "orderId") orderId: UUID): ResponseEntity<*> {
        orderService.deleteOrder(orderId)
        return ResponseEntity("OK", HttpStatus.OK)
    }

    @GetMapping(value = ["/order/{orderId}/items"])
    fun getOrderItems(@PathVariable(value = "orderId") orderId: UUID): ResponseEntity<List<OrderItemDto>> {
        val result = orderService.getOrderItems(orderId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PostMapping(value = ["/order/{orderId}/items"])
    fun saveOrderItem(@PathVariable(value = "orderId") orderId: UUID,
                      @RequestBody orderItem: OrderItemDto): ResponseEntity<OrderDto> {
        val result = orderService.saveOrderItem(orderId, orderItem)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping(value = ["/order/items/{itemId}"])
    fun getOrderItem(@PathVariable(value = "itemId") itemId: UUID): ResponseEntity<OrderItemDto> {
        val result = orderService.getOrderItem(itemId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PutMapping(value = ["/order/items/{itemId}"])
    fun updateOrderItem(@PathVariable(value = "itemId") itemId: UUID,
                        @RequestBody orderItemDto: OrderItemDto): ResponseEntity<OrderItemDto> {
        val result = orderService.updateOrderItem(itemId, orderItemDto)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @DeleteMapping(value = ["/order/items/{itemId}"])
    fun deleteOrderItem(@PathVariable(value = "itemId") itemId: UUID): ResponseEntity<*> {
        orderService.deleteOrderItem(itemId)
        return ResponseEntity("OK", HttpStatus.OK)
    }

    companion object {
        private val LOG = getLogger()
    }
}
