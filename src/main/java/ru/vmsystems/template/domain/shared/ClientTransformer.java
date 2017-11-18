package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.ClientEntity;
import ru.vmsystems.template.domain.model.ClientTypeEntity;
import ru.vmsystems.template.interfaces.dto.ClientDto;

public class ClientTransformer {

    public static ClientDto toDto(ClientEntity entity) {
        ClientDto dto = new ClientDto();
        dto.setId(entity.getId());
        dto.setDesc(entity.getDesc());
        dto.setName(entity.getName());
        dto.setInn(entity.getInn());
        dto.setAccount(entity.getAccount());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setDiscount(entity.getDiscount());

        dto.setType(entity.getClientType() != null ? entity.getClientType().getName() : null);

        return dto;
    }

    public static ClientEntity toEntity(ClientDto client, ClientTypeEntity clientType) {
        ClientEntity entity = new ClientEntity();
        entity.setId(client.getId());
        entity.setDesc(client.getDesc());
        entity.setName(client.getName());
        entity.setInn(client.getInn());
        entity.setAccount(client.getAccount());
        entity.setPhone(client.getPhone());
        entity.setEmail(client.getEmail());
        entity.setDiscount(client.getDiscount());

        entity.setClientType(clientType);

        return entity;
    }
}
