package ru.vmsystems.glasscloud.user

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.handler.BusinessException
import java.util.*

@Service
class UserService(
        private val repository: UserRepository,
        private val sessionService: SessionService
) : BackService() {

    fun getAll(): List<UserDto> {
        return repository.getByCompanyId(sessionService.currentCompanyId)
                .map { it.transform() }
                .toList()
    }

    fun get(id: UUID): UserDto {
        val entity = repository.getById(id) ?: throw BusinessException("Не найден тип материала")
        return entity.transform()
    }

    fun save(dto: UserDto): UserDto {
        val entity = dto.transform(sessionService.currentCompanyId)
        val result = repository.save(entity) ?: throw BusinessException("Ошибка сохранения")
        return result.transform()
    }

    fun delete(orderId: UUID) {
        repository.deleteById(orderId)
    }
}

fun UserDto.transform(currentCompanyId: UUID? = null): UserEntity {
    return UserEntity(
            id = id,
            name = name,
            login = login,
            password = password,
            enabled = enabled,
            role = role,
            email = email,
            phone = phone,
            comment = comment,
            companyId = currentCompanyId ?: companyId!!,
            deleted = deleted
    )
}

fun UserEntity.transform(): UserDto {
    return UserDto(
            id = id,
            name = name,
            login = login,
            password = password,
            enabled = enabled,
            role = role,
            email = email,
            phone = phone,
            comment = comment,
            companyId = companyId,
            deleted = deleted
    )
}
