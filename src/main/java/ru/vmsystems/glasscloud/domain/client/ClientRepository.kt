package ru.vmsystems.glasscloud.domain.client


import org.springframework.data.repository.CrudRepository
import java.util.*

interface ClientRepository : CrudRepository<ClientEntity, UUID>

interface ClientTypeRepository : CrudRepository<ClientTypeEntity, UUID> {
    fun findByName(name: String): ClientTypeEntity
}