package ru.vmsystems.template.domain.service;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.*;
import ru.vmsystems.template.infrastructure.persistence.*;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    @NotNull
    private final DozerBeanMapper mapper;

    @Autowired
    public OrderService(@NotNull OrderRepository orderRepository,
                        @NotNull OrderItemRepository orderItemRepository,
                        @NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                        @NotNull ClientRepository clientRepository,
                        @NotNull MaterialRepository materialRepository,
                        @NotNull DozerBeanMapper mapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.clientRepository = clientRepository;
        this.materialRepository = materialRepository;
        this.mapper = mapper;
    }

    //todo сделать проверку на принадлежность заказа пользователю
    public List<OrderDto> getOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByReceptionOfOrder(Long receptionOfOrder) {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .filter(order -> receptionOfOrder.equals(order.getReceptionOfOrder().getId()))
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
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
        OrderDto result = mapper.map(order, OrderDto.class);

        List<OrderItemDto> items = Lists.newArrayList();
        orderItemRepository.getByOrderId(order.getId())
                .forEach(item -> items.add(mapper.map(item, OrderItemDto.class)));
        result.setItems(items);

        return result;
    }

    @NotNull
    public OrderDto newOrder(@NotNull OrderDto order) {
        ReceptionOfOrderEntity receptionOfOrder = receptionOfOrderRepository.findOne(order.getReceptionOfOrder().getId());

        //todo
        order.setNumber(receptionOfOrder.getOrderNumPrefix() + "1");
        if (order.getDiscount() == null) {
            order.setDiscount("0");
        }
        order.setCreationDate(new Timestamp(new Date().getTime()).toString());
        order.setDiscountSum("0");
        order.setCount(0);
        order.setSumma("0");
        order.setArea(0.0);
        order.setPerimeter(0.0);

        return updateOrder(order);
    }

    @NotNull
    public OrderDto updateOrder(@NotNull OrderDto order) {
        ClientEntity client = clientRepository.findOne(order.getClient().getId());

        OrderEntity entity = mapper.map(order, OrderEntity.class);
        ReceptionOfOrderEntity receptionOfOrder = receptionOfOrderRepository.findOne(entity.getReceptionOfOrder().getId());

        entity.setClient(client);
        entity.setReceptionOfOrder(receptionOfOrder);
        if (entity.getCreationDate() == null) {
            entity.setCreationDate(new Timestamp(new Date().getTime()));
        }
        entity.setUpdateDate(new Timestamp(new Date().getTime()));
        if (entity.getItems() != null) {
            for (OrderItemEntity item : entity.getItems()) {
                if (item.getCreationDate() == null) {
                    item.setCreationDate(new Timestamp(new Date().getTime()));
                }
                item.setUpdateDate(new Timestamp(new Date().getTime()));
                item.setOrder(entity);
            }
        }

        entity = orderRepository.save(entity);

        return mapper.map(entity, OrderDto.class);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
    }

    public List<OrderItemDto> getOrderItems(Long orderId) {
        List<OrderItemDto> result = Lists.newArrayList();
        orderItemRepository.getByOrderId(orderId)
                .forEach(item -> result.add(mapper.map(item, OrderItemDto.class)));
        return result;
    }

    public OrderDto saveOrderItem(Long orderId, OrderItemDto orderItem) {
        OrderEntity order = orderRepository.findOne(orderId);

        MaterialEntity material = materialRepository.findOne(orderItem.getMaterial().getId());
        OrderItemEntity itemEntity = mapper.map(orderItem, OrderItemEntity.class);
        itemEntity.setMaterial(material);
        itemEntity.setOrder(order);
        itemEntity.setCreationDate(new Timestamp(new Date().getTime()));
        itemEntity.setUpdateDate(new Timestamp(new Date().getTime()));

        order.getItems().add(itemEntity);

        OrderEntity entity = orderRepository.save(order);
        return getFullOrder(entity);
    }

    public void deleteOrderItem(Long itemId) {
        orderItemRepository.delete(itemId);
    }
}
