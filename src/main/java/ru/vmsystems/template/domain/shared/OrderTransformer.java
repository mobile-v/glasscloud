package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.OrderItemEntity;
import ru.vmsystems.template.domain.model.OrderOrderEntity;
import ru.vmsystems.template.interfaces.dto.ClientDto;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");

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

        dto.setCreationDate(formatter.format(new Date(order.getCreationDate().getTime())));
        dto.setUpdateDate(formatter.format(new Date(order.getUpdateDate().getTime())));

        ClientDto clientDto = ClientTransformer.toDto(order.getClient());
        dto.setClient(clientDto);

        return dto;
    }
}
