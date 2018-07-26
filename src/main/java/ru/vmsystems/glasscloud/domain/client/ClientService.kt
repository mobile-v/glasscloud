package ru.vmsystems.glasscloud.domain.client

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.SessionService
import java.util.*

@Service
class ClientService(private val clientRepository: ClientRepository,
                    private val sessionService: SessionService,
                    private val clientTypeRepository: ClientTypeRepository) {

    val clientTypes: List<ClientTypeDto>
        get() = clientTypeRepository.findAll()
                .map { it.transform() }

    fun get(): List<ClientDto> {
        return clientRepository.findAll()
                .map { it.transform() }
    }

    operator fun get(orderId: UUID): ClientDto {
        val client = clientRepository.getById(orderId) ?: throw RuntimeException()

        return client.transform()
    }

    fun create(dto: ClientDto): ClientDto {
        return update(dto = dto)
    }

    fun update(id: UUID? = null, dto: ClientDto): ClientDto {
        val currentReception = sessionService.currentReception ?: throw RuntimeException()

        val client = dto.transform(id, currentReception.companyId)
        val entity = clientRepository.save(client)

        return entity.transform()
    }

    fun delete(id: UUID) {
        clientRepository.getById(id)?.let {
            val entity = it.copy(deleted = true)
            clientRepository.save(entity)
        }
    }

}

private fun ClientDto.transform(id: UUID? = null, companyId: UUID): ClientEntity {
    return ClientEntity(
            id = id,
            name = name,
            deleted = deleted,
            inn = inn,
            account = account,
            phone = phone,
            email = email,
            description = description,
            discount = discount,
            clientType = clientType,
            companyId = companyId
    )
}

private fun ClientEntity.transform(): ClientDto {
    return ClientDto(
            id = id,
            name = name,
            deleted = deleted,
            inn = inn,
            account = account,
            phone = phone,
            email = email,
            description = description,
            discount = discount,
            clientType = clientType,
            companyId = companyId
    )
}

private fun ClientTypeEntity.transform(): ClientTypeDto {
    return ClientTypeDto(
            id = id,
            name = name,
            deleted = deleted
    )
}
