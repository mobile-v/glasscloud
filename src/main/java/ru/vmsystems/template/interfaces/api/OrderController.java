package ru.vmsystems.template.interfaces.api;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.ClientEntity;
import ru.vmsystems.template.domain.model.OrderEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.domain.shared.OrderTransformer;
import ru.vmsystems.template.infrastructure.persistence.ClientRepository;
import ru.vmsystems.template.infrastructure.persistence.OrderItemRepository;
import ru.vmsystems.template.infrastructure.persistence.OrderRepository;
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public final class OrderController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @NotNull
    private final OrderRepository orderRepository;
    @NotNull
    private final OrderItemRepository orderItemRepository;
    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    @NotNull
    private final ClientRepository clientRepository;

    @Autowired
    public OrderController(@NotNull OrderRepository orderRepository,
                           @NotNull OrderItemRepository orderItemRepository,
                           @NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                           @NotNull ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.clientRepository = clientRepository;
    }

    //http://localhost:8080/api/order
    @NotNull
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getOrders() {

        List<OrderDto> result = new ArrayList<>();
        orderRepository.findAll()
                .forEach(order -> result.add(OrderTransformer.toDto(order)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1
    @NotNull
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrder(@PathVariable(value = "orderId") Long orderId) {

        OrderDto result;
        OrderEntity order = orderRepository.findOne(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        result = OrderTransformer.toDto(order);
        List<OrderItemDto> items = Lists.newArrayList();
        orderItemRepository.getByOrderId(orderId)
                .forEach(item -> items.add(OrderTransformer.toDto(item)));
        result.setItems(items);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto order) {

        ClientEntity client = clientRepository.findOne(order.getClient().getId());
        ReceptionOfOrderEntity receptionOfOrder = receptionOfOrderRepository.findOne(order.getReceptionOfOrder().getId());

        OrderEntity entity = orderRepository.save(OrderTransformer.toEntity(order, client, receptionOfOrder));
        order.setId(entity.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/
    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "orderId") Long orderId) {

        orderRepository.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //http://localhost:8080/api/order/1/items
    @NotNull
    @RequestMapping(value = "/{orderId}/items", method = RequestMethod.GET)
    public ResponseEntity<List<OrderItemDto>> getOrderItems(
            @PathVariable(value = "orderId") Long orderId) {

        List<OrderItemDto> result = Lists.newArrayList();
        orderItemRepository.getByOrderId(orderId)
                .forEach(item -> result.add(OrderTransformer.toDto(item)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
