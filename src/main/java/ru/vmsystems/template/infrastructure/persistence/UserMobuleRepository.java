package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.UsersModuleEntity;

import java.util.List;
import java.util.Optional;

public interface UserMobuleRepository extends CrudRepository<UsersModuleEntity, Long> {
    Optional<UsersModuleEntity> getById(Integer id);
    List<UsersModuleEntity> getModules();
}
