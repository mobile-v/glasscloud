package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.OrderItemEntity;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> getByOrderId(Long orderId);
}
