package ru.vmsystems.template.domain.service;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.ClientEntity;
import ru.vmsystems.template.infrastructure.persistence.ClientRepository;
import ru.vmsystems.template.infrastructure.persistence.ClientTypeRepository;
import ru.vmsystems.template.interfaces.dto.ClientDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @NotNull
    private final ClientRepository clientRepository;
    @NotNull
    private final ClientTypeRepository clientTypeRepository;
    private final DozerBeanMapper mapper;

    @Autowired
    public ClientService(@NotNull ClientRepository clientRepository,
                         @NotNull ClientTypeRepository clientTypeRepository,
                         DozerBeanMapper mapper) {
        this.clientRepository = clientRepository;
        this.clientTypeRepository = clientTypeRepository;
        this.mapper = mapper;
    }

    public List<ClientDto> get() {
        List<ClientDto> result = new ArrayList<>();
        clientRepository.findAll()
                .forEach(client -> result.add(mapper.map(client, ClientDto.class)));
        return result;
    }

    public Optional<ClientDto> get(@NotNull Long orderId) {
        ClientEntity client = clientRepository.findOne(orderId);
        if (client == null) {
            return Optional.empty();
        }

        ClientDto result = mapper.map(client, ClientDto.class);

        return Optional.of(result);
    }

    @NotNull
    public ClientDto create(@NotNull ClientDto dto) {
        return update(null, dto);
    }

    public ClientDto update(@Nullable Long id, @NotNull ClientDto dto) {
        dto.setId(id);
//        ClientTypeEntity clientType = clientTypeRepository.findByName(dto.getType().getName());

        ClientEntity entity = clientRepository.save(mapper.map(dto, ClientEntity.class));
        dto.setId(entity.getId());

        return mapper.map(entity, ClientDto.class);
    }

    public void delete(Long orderId) {
        clientRepository.delete(orderId);
    }

}
