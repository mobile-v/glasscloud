package ru.vmsystems.template.domain.service;

import org.dozer.DozerBeanMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.ClientEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.infrastructure.persistence.ClientRepository;
import ru.vmsystems.template.infrastructure.persistence.ClientTypeRepository;
import ru.vmsystems.template.interfaces.dto.ClientDto;
import ru.vmsystems.template.interfaces.dto.ClientTypeDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService {

    @NotNull
    private final ClientRepository clientRepository;
    private final SessionService sessionService;
    @NotNull
    private final ClientTypeRepository clientTypeRepository;
    private final DozerBeanMapper mapper;

    @Autowired
    public ClientService(@NotNull ClientRepository clientRepository,
                         SessionService sessionService,
                         @NotNull ClientTypeRepository clientTypeRepository,
                         DozerBeanMapper mapper) {
        this.clientRepository = clientRepository;
        this.sessionService = sessionService;
        this.clientTypeRepository = clientTypeRepository;
        this.mapper = mapper;
    }

    public List<ClientTypeDto> getClientTypes() {
        return StreamSupport.stream(clientTypeRepository.findAll().spliterator(), false)
                .map(i -> mapper.map(i, ClientTypeDto.class))
                .collect(Collectors.toList());
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
        if (dto.getCreationDate() == null) dto.setCreationDate(new Timestamp(new Date().getTime()));
        return update(null, dto);
    }

    public ClientDto update(@Nullable Long id, @NotNull ClientDto dto) {
        dto.setId(id);
        dto.setUpdateDate(new Timestamp(new Date().getTime()));
//
        Optional<ReceptionOfOrderEntity> currentReceptionOfOrder = sessionService.getCurrentReceptionOfOrder();
        if (!currentReceptionOfOrder.isPresent()) {
            return  dto;
        }

        ClientEntity client = mapper.map(dto, ClientEntity.class);
        client.setCompany(currentReceptionOfOrder.get().getCompany());
        ClientEntity entity = clientRepository.save(client);

        dto.setId(entity.getId());

        return mapper.map(entity, ClientDto.class);
    }

    public void delete(Long orderId) {
        clientRepository.delete(orderId);
    }

}
