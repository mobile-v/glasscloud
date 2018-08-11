package ru.vmsystems.glasscloud.domain.material

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.material.color.transform
import ru.vmsystems.glasscloud.domain.material.type.transform
import ru.vmsystems.glasscloud.handler.BusinessException
import java.util.*

@Service
class MaterialService(
        private val repository: MaterialRepository,
        private val sessionService: SessionService
) : BackService() {

    fun getAll(): List<MaterialDto> {
        return repository.getByCompanyId(sessionService.currentCompanyId)
                .map { it.transform() }
                .toList()
    }

    fun get(id: UUID): MaterialDto {
        val entity = repository.getById(id) ?: throw BusinessException("Не найден тип материала")
        return entity.transform()
    }

    fun save(dto: MaterialDto) {
        val entity = dto.transform(sessionService.currentCompanyId)
        repository.save(entity)
    }

    fun delete(orderId: UUID) {
        repository.deleteById(orderId)
    }
}

private fun MaterialDto.transform(currentCompanyId: UUID): MaterialEntity {
    return MaterialEntity(
            id = id,
            deleted = deleted,
            depth = depth,
            length = length,
            width = width,
            price = price,
            description = description,
            companyId = currentCompanyId,
            type = type.transform(),
            color = color.transform()
    )
}

private fun MaterialEntity.transform(): MaterialDto {
    return MaterialDto(
            id = id,
            deleted = deleted,
            depth = depth,
            length = length,
            width = width,
            price = price,
            description = description,
            companyId = companyId,
            type = type.transform(),
            color = color.transform()
    )
}
