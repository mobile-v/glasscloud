package ru.vmsystems.template.domain.service;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.ProcessTypeEntity;
import ru.vmsystems.template.infrastructure.persistence.ProcessTypeRepository;
import ru.vmsystems.template.interfaces.dto.ProcessTypeDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessTypeService extends BackService {

    @Autowired
    private ProcessTypeRepository repository;
    @Autowired
    private DozerBeanMapper mapper;

    public List<ProcessTypeDto> get() {
        return repository.getByCompanyId(getCompanyId()).stream()
                .map(entity -> mapper.map(entity, ProcessTypeDto.class))
                .collect(Collectors.toList());
    }

    public Optional<ProcessTypeDto> get(@NotNull Long id) {
        ProcessTypeEntity entity = repository.findOne(id);
        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(mapper.map(entity, ProcessTypeDto.class));
    }

    @NotNull
    public ProcessTypeDto create(@NotNull ProcessTypeDto dto) {
        return update(null, dto);
    }

    @NotNull
    public ProcessTypeDto update(@Nullable Long id, @NotNull ProcessTypeDto dto) {

        dto.setId(id);
        ProcessTypeEntity entity = mapper.map(dto, ProcessTypeEntity.class);
        entity.setCompany(getCompany());
        entity = repository.save(entity);

        return mapper.map(entity, ProcessTypeDto.class);
    }

    public void delete(Long orderId) {
        repository.delete(orderId);
    }
}
