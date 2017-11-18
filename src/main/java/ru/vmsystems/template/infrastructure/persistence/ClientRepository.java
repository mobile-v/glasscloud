package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {

}
