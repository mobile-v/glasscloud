package ru.vmsystems.glasscloud.domain.material


import org.springframework.data.repository.CrudRepository
import java.util.*

interface MaterialRepository : CrudRepository<MaterialEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<MaterialEntity>
}


interface MaterialTypeRepository : CrudRepository<MaterialTypeEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<MaterialTypeEntity>
}


interface MaterialColorRepository : CrudRepository<MaterialColorEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<MaterialColorEntity>
}