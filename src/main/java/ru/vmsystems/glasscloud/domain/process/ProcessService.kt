package ru.vmsystems.glasscloud.domain.process

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.domain.material.transform
import ru.vmsystems.glasscloud.domain.process.type.transform
import ru.vmsystems.glasscloud.handler.BusinessException
import java.util.*

@Service
class ProcessService(
        private val repository: ProcessRepository,
        private val sessionService: SessionService
) : BackService() {

    fun getAll(): List<ProcessDto> {
        return repository.getByCompanyId(sessionService.currentCompanyId)
                .map { it.transform() }
                .toList()
    }

    fun get(id: UUID): ProcessDto {
        val entity = repository.getById(id) ?: throw BusinessException("Не найдена работа")
        return entity.transform()
    }

    fun save(dto: ProcessDto) {
        val entity = dto.transform(sessionService.currentCompanyId)
        repository.save(entity)
    }

    fun delete(id: UUID) {
        repository.deleteById(id)
    }
}

fun ProcessDto.transform(currentCompanyId: UUID? = null): ProcessEntity {
    return ProcessEntity(
            id = id,
            deleted = deleted,
            description = description,
            depth = depth,
            price = price,
            type = type.transform(),
            material = material.map { it.transform() },
            companyId = currentCompanyId ?: companyId
    )
}

fun ProcessEntity.transform(): ProcessDto {
    return ProcessDto(
            id = id!!,
            deleted = deleted,
            description = description,
            depth = depth,
            price = price,
            material = material.map { it.transform() },
            type = type.transform(),
            companyId = companyId
    )
}
