package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.ClientClientEntity;
import ru.vmsystems.template.interfaces.dto.ClientDto;

class ClientTransformer {

    static ClientDto toDto(ClientClientEntity entity) {
        ClientDto dto = new ClientDto();
        dto.setId(entity.getId());
        dto.setDesc(entity.getDesc());
        dto.setName(entity.getName());
        dto.setInn(entity.getInn());
        dto.setAccount(entity.getAccount());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setDiscount(entity.getDiscount());

        if (entity.getClientType() != null) {
            dto.setType(entity.getClientType().getName());
        }

        return dto;
    }
}
