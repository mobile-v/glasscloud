package ru.vmsystems.template.domain.shared;

import org.jetbrains.annotations.NotNull;
import ru.vmsystems.template.domain.model.ClientEntity;
import ru.vmsystems.template.domain.model.OrderEntity;
import ru.vmsystems.template.domain.model.OrderItemEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.interfaces.dto.OrderDto;
import ru.vmsystems.template.interfaces.dto.OrderItemDto;

import java.sql.Timestamp;
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
        dto.setMaterial(MaterialTransformer.toDto(item.getMaterial()));

        return dto;
    }

    public static OrderDto toDto(OrderEntity order) {
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

        dto.setClient(ClientTransformer.toDto(order.getClient()));
        dto.setReceptionOfOrder(CompanyTransformer.toDto(order.getReceptionOfOrder()));

        return dto;
    }

    public static OrderEntity toEntity(@NotNull OrderDto order,
                                       @NotNull ClientEntity client,
                                       @NotNull ReceptionOfOrderEntity receptionOfOrder) {
        OrderEntity entity = new OrderEntity();

        entity.setId(order.getId());
        entity.setDesc(order.getDesc());
        entity.setAccountNumber(order.getAccountNumber());
        entity.setNumber(order.getNumber());
        entity.setDiscount(order.getDiscount());
        entity.setDiscountSum(order.getDiscountSum());
        entity.setCount(order.getCount());
        entity.setSumma(order.getSumma());
        entity.setArea(order.getArea());
        entity.setPerimeter(order.getPerimeter());

        entity.setCreationDate(new Timestamp(new Date().getTime()));
        entity.setUpdateDate(new Timestamp(new Date().getTime()));

        entity.setClient(client);
        entity.setReceptionOfOrder(receptionOfOrder);

        return entity;
    }
}
