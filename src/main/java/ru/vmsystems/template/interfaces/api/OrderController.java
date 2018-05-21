package ru.vmsystems.template.interfaces.api;

import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.service.OrderService;
import ru.vmsystems.template.domain.service.SessionService;
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;
import ru.vmsystems.template.interfaces.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController("OrderControllerApi")
@RequestMapping("/api")
public class OrderController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @NotNull
    private final OrderService orderService;
    private SessionService sessionService;
    @NotNull
    private final UserRepository userRepository;
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    @NotNull
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public OrderController(@NotNull OrderService orderService,
                           SessionService sessionService,
                           @NotNull HttpServletRequest httpServletRequest,
                           @NotNull UserRepository userRepository,
                           @NotNull ReceptionOfOrderRepository receptionOfOrderRepository) {
        this.orderService = orderService;
        this.sessionService = sessionService;
        this.httpServletRequest = httpServletRequest;
        this.userRepository = userRepository;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
    }

    //http://localhost:8080/api/1/order
    //http://localhost:8080/api/order
    @ApiOperation(value = "Получить список всех заказов по точке выдачи")
    @NotNull
    @RequestMapping(value = {
            "/order",
//            "/{receptionOfOrder}/order",
//            "/order/receptionOfOrder/{receptionOfOrder}"
    }, method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getOrdersByReceptionOfOrder(
//            @PathVariable(value = "receptionOfOrder") Long receptionOfOrder,
            Principal principal) {

        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Optional<ReceptionOfOrderEntity> receptionOfOrder = sessionService.getCurrentReceptionOfOrder();
        if (!receptionOfOrder.isPresent()) return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);

//        Optional<ReceptionOfOrderEntity> receptionOfOrderEntity = ofNullable(receptionOfOrderRepository
//                .findOne(receptionOfOrder.get()))
//                .filter(e -> e.getCompany().getId().equals(user.get().getCompany().getId()));

//        if (!receptionOfOrder.isPresent()) {
//            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
//        }

        List<OrderDto> result = orderService.getOrdersByReceptionOfOrder(receptionOfOrder.get().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/3
    @ApiOperation(value = "Получить заказ по id заказа")
    @NotNull
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrder(@PathVariable(value = "orderId") Long orderId) {

        Optional<OrderDto> order = orderService.getOrder(orderId);
        return order.map(orderDto -> new ResponseEntity<>(orderDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    //http://localhost:8080/api/order/
//    @CrossOrigin(origins = "http://localhost:8080")
    @ApiOperation(value = "Создать новый заказ")
    @RequestMapping(value = "/order", method = RequestMethod.POST)
//    @PreAuthorize("@moduleSecurity.orderPermitted")
    public ResponseEntity<OrderDto> newOrder(@RequestBody OrderDto order, Principal principal) {
        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        LOG.info("-- save --");

        if (order.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        order = orderService.newOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1
    @ApiOperation(value = "Обновить заказ")
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<OrderDto> updateOrder(@PathVariable(value = "orderId") Long orderId,
                                                @RequestBody OrderDto order, Principal principal) {
        Optional<UserEntity> user = userRepository.getByLogin(principal.getName());
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        LOG.info("-- update --");

        order.setId(orderId);
        order = orderService.updateOrder(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        LOG.info("-- options --");
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, RequestMethod.POST.name());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/
    @ApiOperation(value = "Удалить заказ по id заказа")
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(new Result("OK"), HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/items
    @NotNull
    @RequestMapping(value = "/order/{orderId}/items", method = RequestMethod.GET)
    public ResponseEntity<List<OrderItemDto>> getOrderItems(@PathVariable(value = "orderId") Long orderId) {
        List<OrderItemDto> result = orderService.getOrderItems(orderId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/items/
    @RequestMapping(value = "/order/{orderId}/items", method = RequestMethod.POST)
    public ResponseEntity<OrderDto> saveOrderItem(@PathVariable(value = "orderId") Long orderId,
                                                  @RequestBody OrderItemDto orderItem) {
        OrderDto result = orderService.saveOrderItem(orderId, orderItem);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/order/items/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<OrderItemDto> getOrderItem(@PathVariable(value = "itemId") Long itemId) {
        OrderItemDto result = orderService.getOrderItem(itemId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/order/items/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable(value = "itemId") Long itemId,
                                                  @RequestBody OrderItemDto orderItemDto) {
        OrderItemDto result = orderService.updateOrderItem(itemId, orderItemDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/items/1
    @RequestMapping(value = "/order/items/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> deleteOrderItem(@PathVariable(value = "itemId") Long itemId) {
        orderService.deleteOrderItem(itemId);
        return new ResponseEntity<>(new Result("OK"), HttpStatus.OK);
    }
}
