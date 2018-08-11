package ru.vmsystems.glasscloud.domain.material.type

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.material.MaterialTypeDto
import ru.vmsystems.glasscloud.domain.material.MaterialTypeEntity
import ru.vmsystems.glasscloud.domain.material.MaterialTypeRepository
import ru.vmsystems.glasscloud.handler.BusinessException
import java.util.*

@Service
class MaterialTypeService(
        private val repository: MaterialTypeRepository,
        private val sessionService: SessionService
) : BackService() {

    fun getAll(): List<MaterialTypeDto> {
        return repository.getByCompanyId(sessionService.currentCompanyId)
                .map { it.transform() }
                .toList()
    }

    fun get(id: UUID): MaterialTypeDto {
        val entity = repository.getById(id) ?: throw BusinessException("Не найден тип материала")
        return entity.transform()
    }

    fun save(dto: MaterialTypeDto) {
        val entity = dto.transform(sessionService.currentCompanyId)
        repository.save(entity)
    }

    fun delete(orderId: UUID) {
        repository.deleteById(orderId)
    }
}

fun MaterialTypeDto.transform(currentCompanyId: UUID? = null): MaterialTypeEntity {
    return MaterialTypeEntity(
            id = id,
            name = name,
            deleted = deleted,
            companyId = currentCompanyId ?: companyId!!
    )
}

fun MaterialTypeEntity.transform(): MaterialTypeDto {
    return MaterialTypeDto(
            id = id!!,
            name = name,
            deleted = deleted,
            companyId = companyId
    )
}
