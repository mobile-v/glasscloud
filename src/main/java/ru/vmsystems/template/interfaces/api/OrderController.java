package ru.vmsystems.template.interfaces.api;

import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.service.OrderService;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @NotNull
    private final OrderService orderService;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public OrderController(@NotNull OrderService orderService,
                           @NotNull HttpServletRequest httpServletRequest,
                           @NotNull UserRepository userRepository) {
        this.orderService = orderService;
        this.httpServletRequest = httpServletRequest;
        this.userRepository = userRepository;
    }

    //http://localhost:8080/api/order
    @ApiOperation(value = "Получить список всех заказов")
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> result = orderService.getOrders();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1
    @ApiOperation(value = "Получить заказ по id заказа")
    @NotNull
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrder(@PathVariable(value = "orderId") Long orderId) {

        Optional<OrderDto> order = orderService.getOrder(orderId);
        return order.map(orderDto ->
                new ResponseEntity<>(orderDto, HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    //http://localhost:8080/api/order/
    @ApiOperation(value = "Создать новый / обновить заказ")
    @RequestMapping(value = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @PreAuthorize("@moduleSecurity.orderPermitted")
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto order, Principal principal) {
        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (order.getId() == null) {
            order = orderService.newOrder(order);
        } else {
            order = orderService.saveOrder(order);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/
    @ApiOperation(value = "Удалить заказ по id заказа")
    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/items
    @NotNull
    @RequestMapping(value = "/{orderId}/items", method = RequestMethod.GET)
    public ResponseEntity<List<OrderItemDto>> getOrderItems(@PathVariable(value = "orderId") Long orderId) {
        List<OrderItemDto> result = orderService.getOrderItems(orderId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/items/
    @RequestMapping(value = "/{orderId}/items", method = RequestMethod.POST)
    public ResponseEntity<OrderDto> saveOrderItem(@PathVariable(value = "orderId") Long orderId,
                                                  @RequestBody OrderItemDto orderItem) {
        OrderDto result = orderService.saveOrderItem(orderId, orderItem);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrderItem(@PathVariable(value = "itemId") Long itemId) {
        orderService.deleteOrderItem(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
