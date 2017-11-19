package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.interfaces.dto.CompanyDto;

class CompanyTransformer {

    static CompanyDto toDto(CompanyEntity entity) {
        CompanyDto dto = new CompanyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}
