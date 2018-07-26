package ru.vmsystems.glasscloud.domain.order

import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.company.ReceptionRepository
import ru.vmsystems.glasscloud.user.UserRepository
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController("OrderControllerApi")
@RequestMapping("/api")
class OrderController @Autowired
constructor(private val orderService: OrderService,
            private val sessionService: SessionService,
            private val httpServletRequest: HttpServletRequest,
            private val userRepository: UserRepository,
            private val receptionOfOrderRepository: ReceptionRepository) {

    @ApiOperation(value = "Получить список всех заказов по точке выдачи")
    @GetMapping(value = ["/order"])
    //            "/order/receptionOfOrder/{receptionOfOrder}"
    fun getOrdersByReceptionOfOrder(
            principal: Principal): ResponseEntity<List<OrderDto>> {

        val user = userRepository.getByLogin(principal.name) ?: return ResponseEntity(HttpStatus.FORBIDDEN)

        val receptionOfOrder = sessionService.currentReception ?: return ResponseEntity(emptyList(), HttpStatus.OK)

        val result = orderService.getOrdersByReception(receptionOfOrder.id)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @ApiOperation(value = "Получить заказ по id заказа")
    @GetMapping(value = ["/order/{orderId}"])
    fun getOrder(@PathVariable(value = "orderId") orderId: UUID): ResponseEntity<OrderDto> {

        val order = orderService.getOrder(orderId)
        return order.map { orderDto -> ResponseEntity(orderDto, HttpStatus.OK) }
                .orElseGet { ResponseEntity(HttpStatus.NO_CONTENT) }
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
    fun deleteOrder(@PathVariable(value = "orderId") orderId: Long?): ResponseEntity<*> {
        orderService.deleteOrder(orderId)
        return ResponseEntity("OK", HttpStatus.OK)
    }

    //http://localhost:8080/api/order/1/items
    @RequestMapping(value = "/order/{orderId}/items", method = arrayOf(RequestMethod.GET))
    fun getOrderItems(@PathVariable(value = "orderId") orderId: Long?): ResponseEntity<List<OrderItemDto>> {
        val result = orderService.getOrderItems(orderId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    //http://localhost:8080/api/order/1/items/
    @RequestMapping(value = "/order/{orderId}/items", method = arrayOf(RequestMethod.POST))
    fun saveOrderItem(@PathVariable(value = "orderId") orderId: Long?,
                      @RequestBody orderItem: OrderItemDto): ResponseEntity<OrderDto> {
        val result = orderService.saveOrderItem(orderId, orderItem)
        return ResponseEntity(result, HttpStatus.OK)
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/order/items/{itemId}", method = arrayOf(RequestMethod.GET))
    fun getOrderItem(@PathVariable(value = "itemId") itemId: Long?): ResponseEntity<OrderItemDto> {
        val result = orderService.getOrderItem(itemId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/order/items/{itemId}", method = arrayOf(RequestMethod.PUT))
    fun updateOrderItem(@PathVariable(value = "itemId") itemId: Long?,
                        @RequestBody orderItemDto: OrderItemDto): ResponseEntity<OrderItemDto> {
        val result = orderService.updateOrderItem(itemId, orderItemDto)
        return ResponseEntity(result, HttpStatus.OK)
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/order/items/{itemId}", method = arrayOf(RequestMethod.DELETE))
    fun deleteOrderItem(@PathVariable(value = "itemId") itemId: Long?): ResponseEntity<Result> {
        orderService.deleteOrderItem(itemId)
        return ResponseEntity(Result("OK"), HttpStatus.OK)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(OrderController::class.java)
    }
}
