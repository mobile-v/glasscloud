package ru.vmsystems.glasscloud.domain.material.color

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.material.MaterialColorDto
import ru.vmsystems.glasscloud.domain.material.MaterialColorEntity
import ru.vmsystems.glasscloud.domain.material.MaterialColorRepository
import ru.vmsystems.glasscloud.handler.BusinessException
import java.util.*

@Service
class MaterialColorService(
        private val repository: MaterialColorRepository,
        private val sessionService: SessionService
) : BackService() {

    fun getAll(): List<MaterialColorDto> {
        return repository.getByCompanyId(sessionService.currentCompanyId)
                .map { it.transform() }
                .toList()
    }

    fun get(id: UUID): MaterialColorDto {
        val entity = repository.getById(id) ?: throw BusinessException("Не найден цвет материала")
        return entity.transform()
    }

    fun save(dto: MaterialColorDto) {
        val entity = dto.transform(sessionService.currentCompanyId)
        repository.save(entity)
    }

    fun delete(orderId: UUID) {
        repository.deleteById(orderId)
    }
}

fun MaterialColorDto.transform(currentCompanyId: UUID? = null): MaterialColorEntity {
    return MaterialColorEntity(
            id = id,
            name = name,
            deleted = deleted,
            companyId = currentCompanyId ?: companyId!!
    )
}

fun MaterialColorEntity.transform(): MaterialColorDto {
    return MaterialColorDto(
            id = id!!,
            name = name,
            deleted = deleted,
            companyId = companyId
    )
}
