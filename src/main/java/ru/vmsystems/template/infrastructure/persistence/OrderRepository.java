package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.OrderOrderEntity;

public interface OrderRepository extends CrudRepository<OrderOrderEntity, Long> {

}
