package ru.vmsystems.glasscloud.domain.material


import org.springframework.data.repository.CrudRepository
import java.util.*

interface MaterialRepository : CrudRepository<MaterialEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<MaterialEntity>
    fun getById(id: UUID): MaterialEntity?
}


interface MaterialTypeRepository : CrudRepository<MaterialTypeEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<MaterialTypeEntity>
    fun getById(id: UUID): MaterialTypeEntity?
}


interface MaterialColorRepository : CrudRepository<MaterialColorEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<MaterialColorEntity>
    fun getById(id: UUID): MaterialColorEntity?
}