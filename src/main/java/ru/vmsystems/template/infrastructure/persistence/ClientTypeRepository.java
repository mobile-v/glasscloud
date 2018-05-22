package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.ClientTypeEntity;

import java.util.List;

public interface ClientTypeRepository extends CrudRepository<ClientTypeEntity, Long> {

    ClientTypeEntity findByName(String name);
}
