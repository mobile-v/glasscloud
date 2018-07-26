package ru.vmsystems.glasscloud.domain.client


import org.springframework.data.repository.CrudRepository

interface ClientRepository : CrudRepository<ClientEntity, Long>

interface ClientTypeRepository : CrudRepository<ClientTypeEntity, Long> {
    fun findByName(name: String): ClientTypeEntity
}