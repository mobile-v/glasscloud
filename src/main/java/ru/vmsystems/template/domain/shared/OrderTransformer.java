package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.OrderItemEntity;
import ru.vmsystems.template.domain.model.OrderOrderEntity;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;
import ru.vmsystems.template.interfaces.dto.OrderDto;

public class OrderTransformer {

    public static OrderItemDto toDto(OrderItemEntity item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setDesc(item.getDesc());
        dto.setNumber(item.getNumber());
        dto.setLength(item.getLength());
        dto.setCount(item.getCount());
        dto.setArea(item.getArea());
        dto.setPerimeter(item.getPerimeter());
        dto.setProcessSum(item.getProcessSum());
        dto.setSumma(item.getSumma());
        dto.setMaterialId(item.getMaterialId());

        return dto;
    }

    public static OrderDto toDto(OrderOrderEntity order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setDesc(order.getDesc());
        dto.setAccountNumber(order.getAccountNumber());
        dto.setNumber(order.getNumber());
        dto.setDiscount(order.getDiscount());
        dto.setDiscountSum(order.getDiscountSum());
        dto.setCount(order.getCount());
        dto.setSumma(order.getSumma());
        dto.setArea(order.getArea());
        dto.setPerimeter(order.getPerimeter());
        dto.setClientId(order.getClientId());
        dto.setReceptionOfOrderId(order.getReceptionOfOrderId());
        dto.setClientTypeId(order.getClientTypeId());

        return dto;
    }
}
