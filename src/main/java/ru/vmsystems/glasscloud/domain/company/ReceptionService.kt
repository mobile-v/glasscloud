package ru.vmsystems.glasscloud.domain.company

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.BackService
import ru.vmsystems.glasscloud.user.UserRepository
import java.util.*

@Service
class ReceptionService(private val receptionRepository: ReceptionRepository,
                       private val companyRepository: CompanyRepository,
                       private val userRepository: UserRepository) : BackService() {

    fun get(): List<ReceptionDto> {
        val result = ArrayList<ReceptionDto>()
        receptionRepository.getByCompanyId(companyId)
                .map { it.transform() }
        return result
    }

    operator fun get(orderId: UUID): ReceptionDto {
        return receptionRepository.findById(orderId)
                .orElseThrow { RuntimeException() }
                .transform()
    }

    fun create(dto: ReceptionDto): ReceptionDto {
        return update(receptionDto = dto)
    }

    fun update(id: UUID? = null, receptionDto: ReceptionDto): ReceptionDto {
        val receptionEntity = receptionDto.transform(id)
        val entity = receptionRepository.save(receptionEntity)
        return entity.transform()
    }

    fun delete(orderId: UUID) {
        receptionRepository.deleteById(orderId)
    }

    fun getByUserName(userName: String): List<ReceptionDto> {
        val user = userRepository.getByLogin(userName) ?: throw RuntimeException()

        return receptionRepository.getByCompanyId(user.companyId)
                .map { it.transform() }
    }
}

fun ReceptionDto.transform(id: UUID? = null): ReceptionEntity {
    return ReceptionEntity(
            id = id,
            name = name,
            deleted = deleted,
            orderNumPrefix = orderNumPrefix,
            description = description,
            address = address,
            phone = phone,
            companyId = companyId
    )
}

fun ReceptionEntity.transform(): ReceptionDto {
    return ReceptionDto(
            id = id,
            name = name,
            deleted = deleted,
            orderNumPrefix = orderNumPrefix,
            description = description,
            address = address,
            phone = phone,
            companyId = companyId
    )
}
