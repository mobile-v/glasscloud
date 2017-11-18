package ru.vmsystems.template.domain.shared;

import ru.vmsystems.template.domain.model.MaterialEntity;
import ru.vmsystems.template.interfaces.dto.MaterialDto;

public class MaterialTransformer {

    public static MaterialDto toDto(MaterialEntity entity) {
        MaterialDto dto = new MaterialDto();

        dto.setId(entity.getId());
        dto.setDesc(entity.getDesc());
        dto.setDepth(entity.getDepth());
        dto.setLength(entity.getLength());
        dto.setWidth(entity.getWidth());
        dto.setPrice(entity.getPrice());
        dto.setColor(entity.getColor() != null ? entity.getColor().getName() : null);
        dto.setType(entity.getType() != null ? entity.getType().getName() : null);

        return dto;
    }
}
