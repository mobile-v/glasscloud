package ru.vmsystems.template.domain.service;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.MaterialColorEntity;
import ru.vmsystems.template.infrastructure.persistence.MaterialColorRepository;
import ru.vmsystems.template.interfaces.dto.MaterialColorDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ColorService extends BackService {

    @NotNull
    private final MaterialColorRepository repository;
    @NotNull
    private final DozerBeanMapper mapper;

    @Autowired
    public ColorService(@NotNull MaterialColorRepository repository,
                        @NotNull DozerBeanMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MaterialColorDto> get() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(entity -> getCompanyId().equals(entity.getCompany().getId()))
                .map(entity -> mapper.map(entity, MaterialColorDto.class))
                .collect(Collectors.toList());
    }

    public Optional<MaterialColorDto> get(@NotNull Long id) {
        MaterialColorEntity entity = repository.findOne(id);
        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(mapper.map(entity, MaterialColorDto.class));
    }

    @NotNull
    public MaterialColorDto create(@NotNull MaterialColorDto dto) {
        return update(null, dto);
    }

    @NotNull
    public MaterialColorDto update(@Nullable Long id, @NotNull MaterialColorDto dto) {

        dto.setId(id);
        MaterialColorEntity entity = mapper.map(dto, MaterialColorEntity.class);
        entity.setCompany(getCompany());
        entity = repository.save(entity);

        return mapper.map(entity, MaterialColorDto.class);
    }

    public void delete(Long orderId) {
        repository.delete(orderId);
    }
}
