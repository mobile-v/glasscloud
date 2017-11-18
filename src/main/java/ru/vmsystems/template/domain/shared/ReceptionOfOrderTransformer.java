package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

public class ReceptionOfOrderTransformer {

    public static ReceptionOfOrderDto toDto(ReceptionOfOrderEntity entity) {
        ReceptionOfOrderDto dto = new ReceptionOfOrderDto();
        dto.setId(entity.getId());
        dto.setDesc(entity.getDesc());
        dto.setName(entity.getName());
        dto.setOrderNumPrefix(entity.getOrderNumPrefix());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());

        if (entity.getCompany() != null) {
            dto.setCompany(CompanyTransformer.toDto(entity.getCompany()));
        }

        return dto;
    }

    public static ReceptionOfOrderEntity toEntity(ReceptionOfOrderDto dto, CompanyEntity company) {
        ReceptionOfOrderEntity entity = new ReceptionOfOrderEntity();

        entity.setId(dto.getId());
        entity.setDesc(dto.getDesc());
        entity.setName(dto.getName());
        entity.setOrderNumPrefix(dto.getOrderNumPrefix());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setCompany(company);

        return entity;
    }
}
