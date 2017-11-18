package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.CompanyReceptionoforderEntity;
import ru.vmsystems.template.interfaces.dto.CompanyDto;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

class CompanyTransformer {

    static ReceptionOfOrderDto toDto(CompanyReceptionoforderEntity entity) {
        ReceptionOfOrderDto dto = new ReceptionOfOrderDto();
        dto.setId(entity.getId());
        dto.setDesc(entity.getDesc());
        dto.setOrderNumPrefix(entity.getOrderNumPrefix());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());

        if (entity.getCompany() != null) {
            dto.setCompany(toDto(entity.getCompany()));
        }

        return dto;
    }

    static CompanyDto toDto(CompanyEntity entity) {
        CompanyDto dto = new CompanyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}
