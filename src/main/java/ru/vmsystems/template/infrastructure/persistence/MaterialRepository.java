package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.MaterialEntity;

public interface MaterialRepository extends CrudRepository<MaterialEntity, Long> {

}
