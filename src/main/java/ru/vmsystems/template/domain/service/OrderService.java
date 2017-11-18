package ru.vmsystems.template.domain.service;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.*;
import ru.vmsystems.template.domain.shared.OrderTransformer;
import ru.vmsystems.template.infrastructure.persistence.*;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @NotNull
    private final OrderRepository orderRepository;
    @NotNull
    private final OrderItemRepository orderItemRepository;
    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    @NotNull
    private final ClientRepository clientRepository;
    @NotNull
    private final MaterialRepository materialRepository;

    @Autowired
    public OrderService(@NotNull OrderRepository orderRepository,
                        @NotNull OrderItemRepository orderItemRepository,
                        @NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                        @NotNull ClientRepository clientRepository,
                        @NotNull MaterialRepository materialRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.clientRepository = clientRepository;
        this.materialRepository = materialRepository;
    }

    public List<OrderDto> getOrders() {
        List<OrderDto> result = new ArrayList<>();
        orderRepository.findAll()
                .forEach(order -> result.add(OrderTransformer.toDto(order)));
        return result;
    }

    public Optional<OrderDto> getOrder(@NotNull Long orderId) {
        OrderEntity order = orderRepository.findOne(orderId);
        if (order == null) {
            return Optional.empty();
        }

        OrderDto result = getFullOrder(order);

        return Optional.of(result);
    }

    @NotNull
    private OrderDto getFullOrder(OrderEntity order) {
        OrderDto result = OrderTransformer.toDto(order);

        List<OrderItemDto> items = Lists.newArrayList();
        orderItemRepository.getByOrderId(order.getId())
                .forEach(item -> items.add(OrderTransformer.toDto(item)));
        result.setItems(items);

        return result;
    }

    public void saveOrder(@NotNull OrderDto order) {
        ClientEntity client = clientRepository.findOne(order.getClient().getId());
        ReceptionOfOrderEntity receptionOfOrder = receptionOfOrderRepository.findOne(order.getReceptionOfOrder().getId());

        OrderEntity entity = orderRepository.save(OrderTransformer.toEntity(order, client, receptionOfOrder));
        order.setId(entity.getId());
    }

    public void deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
    }

    public List<OrderItemDto> getOrderItems(Long orderId) {
        List<OrderItemDto> result = Lists.newArrayList();
        orderItemRepository.getByOrderId(orderId)
                .forEach(item -> result.add(OrderTransformer.toDto(item)));
        return result;
    }

    public OrderDto saveOrderItem(Long orderId, OrderItemDto orderItem) {
        OrderEntity order = orderRepository.findOne(orderId);

        MaterialEntity material = materialRepository.findOne(orderItem.getMaterial().getId());
        OrderItemEntity itemEntity = OrderTransformer.toEntity(orderItem, material);
        itemEntity.setOrder(order);

        order.getItems().add(itemEntity);

        OrderEntity entity = orderRepository.save(order);
        return getFullOrder(entity);
    }

    public void deleteOrderItem(Long itemId) {
        orderItemRepository.delete(itemId);
    }
}
