package ru.vmsystems.glasscloud.domain.process.type

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.process.ProcessTypeDto
import ru.vmsystems.glasscloud.domain.process.ProcessTypeEntity
import ru.vmsystems.glasscloud.domain.process.ProcessTypeRepository
import ru.vmsystems.glasscloud.handler.BusinessException
import java.util.*

@Service
class ProcessTypeService(
        private val repository: ProcessTypeRepository,
        private val sessionService: SessionService
) : BackService() {

    fun getAll(): List<ProcessTypeDto> {
        return repository.getByCompanyId(sessionService.currentCompanyId)
                .map { it.transform() }
                .toList()
    }

    fun get(id: UUID): ProcessTypeDto {
        val entity = repository.getById(id) ?: throw BusinessException("Не найден тип работ")
        return entity.transform()
    }

    fun save(dto: ProcessTypeDto) {
        val entity = dto.transform(sessionService.currentCompanyId)
        repository.save(entity)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }
}

fun ProcessTypeDto.transform(currentCompanyId: UUID? = null): ProcessTypeEntity {
    return ProcessTypeEntity(
            id = id,
            name = name,
            deleted = deleted,
            description = description,
            companyId = currentCompanyId ?: companyId
    )
}

fun ProcessTypeEntity.transform(): ProcessTypeDto {
    return ProcessTypeDto(
            id = id!!,
            name = name,
            deleted = deleted,
            description = description,
            companyId = companyId
    )
}
