package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;

public interface ReceptionOfOrderRepository extends CrudRepository<ReceptionOfOrderEntity, Long> {

}
