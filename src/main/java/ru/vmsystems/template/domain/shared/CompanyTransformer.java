package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.interfaces.dto.CompanyDto;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

class CompanyTransformer {

    static ReceptionOfOrderDto toDto(ReceptionOfOrderEntity entity) {
        ReceptionOfOrderDto dto = new ReceptionOfOrderDto();
        dto.setId(entity.getId());
        dto.setDesc(entity.getDesc());
        dto.setOrderNumPrefix(entity.getOrderNumPrefix());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());

        dto.setCompany(entity.getCompany() != null ? toDto(entity.getCompany()) : null);

        return dto;
    }

    static CompanyDto toDto(CompanyEntity entity) {
        CompanyDto dto = new CompanyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}
