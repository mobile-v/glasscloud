package ru.vmsystems.template.domain.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.*;
import ru.vmsystems.template.domain.shared.ClientTransformer;
import ru.vmsystems.template.infrastructure.persistence.*;
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

    @Autowired
    public ClientService(@NotNull ClientRepository clientRepository,
                         @NotNull ClientTypeRepository clientTypeRepository) {
        this.clientRepository = clientRepository;
        this.clientTypeRepository = clientTypeRepository;
    }

    public List<ClientDto> get() {
        List<ClientDto> result = new ArrayList<>();
        clientRepository.findAll()
                .forEach(client -> result.add(ClientTransformer.toDto(client)));
        return result;
    }

    public Optional<ClientDto> get(@NotNull Long orderId) {
        ClientEntity order = clientRepository.findOne(orderId);
        if (order == null) {
            return Optional.empty();
        }

        ClientDto result = ClientTransformer.toDto(order);

        return Optional.of(result);
    }

    public void save(@NotNull ClientDto client) {
        ClientTypeEntity clientType = clientTypeRepository.findByName(client.getType());

        ClientEntity entity = clientRepository.save(ClientTransformer.toEntity(client, clientType));
        client.setId(entity.getId());
    }

    public void delete(Long orderId) {
        clientRepository.delete(orderId);
    }

}
