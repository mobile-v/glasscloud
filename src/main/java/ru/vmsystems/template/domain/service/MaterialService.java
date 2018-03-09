package ru.vmsystems.template.domain.service;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.MaterialEntity;
import ru.vmsystems.template.infrastructure.persistence.MaterialRepository;
import ru.vmsystems.template.interfaces.dto.MaterialDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialService extends BackService {

    @Autowired
    private MaterialRepository repository;
    @Autowired
    private DozerBeanMapper mapper;

    public List<MaterialDto> get() {
        return repository.getByCompanyId(getCompanyId()).stream()
                .map(entity -> mapper.map(entity, MaterialDto.class))
                .collect(Collectors.toList());
    }

    public Optional<MaterialDto> get(@NotNull Long id) {
        MaterialEntity entity = repository.findOne(id);
        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(mapper.map(entity, MaterialDto.class));
    }

    @NotNull
    public MaterialDto create(@NotNull MaterialDto dto) {
        return update(null, dto);
    }

    @NotNull
    public MaterialDto update(@Nullable Long id, @NotNull MaterialDto dto) {

        dto.setId(id);
        MaterialEntity entity = mapper.map(dto, MaterialEntity.class);
        entity.setCompany(getCompany());
        entity = repository.save(entity);

        return mapper.map(entity, MaterialDto.class);
    }

    public void delete(Long orderId) {
        repository.delete(orderId);
    }
}
