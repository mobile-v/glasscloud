package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.tag.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Long> {
    Optional<TagEntity> getById(Integer id);

    List<TagEntity> findByIdCpuAndType(Integer idCpu, String type);
}
