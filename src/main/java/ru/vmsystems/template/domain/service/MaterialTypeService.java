package ru.vmsystems.template.domain.service;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.MaterialTypeEntity;
import ru.vmsystems.template.infrastructure.persistence.MaterialTypeRepository;
import ru.vmsystems.template.interfaces.dto.MaterialTypeDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MaterialTypeService extends BackService {

    @NotNull
    private final MaterialTypeRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;

    @Autowired
    public MaterialTypeService(@NotNull MaterialTypeRepository repository,
                               @NotNull DozerBeanMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MaterialTypeDto> get() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(entity -> getCompanyId().equals(entity.getCompany().getId()))
                .map(entity -> mapper.map(entity, MaterialTypeDto.class))
                .collect(Collectors.toList());
    }

    public Optional<MaterialTypeDto> get(@NotNull Long id) {
        MaterialTypeEntity entity = repository.findOne(id);
        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(mapper.map(entity, MaterialTypeDto.class));
    }

    @NotNull
    public MaterialTypeDto create(@NotNull MaterialTypeDto dto) {
        return update(null, dto);
    }

    @NotNull
    public MaterialTypeDto update(@Nullable Long id, @NotNull MaterialTypeDto dto) {

        dto.setId(id);
        MaterialTypeEntity entity = mapper.map(dto, MaterialTypeEntity.class);
        entity.setCompany(getCompany());
        entity = repository.save(entity);

        return mapper.map(entity, MaterialTypeDto.class);
    }

    public void delete(Long orderId) {
        repository.delete(orderId);
    }
}
