package ru.vmsystems.glasscloud.domain.process


import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProcessRepository : CrudRepository<ProcessEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<ProcessEntity>
    fun getById(id: UUID): ProcessEntity?
}

interface ProcessTypeRepository : CrudRepository<ProcessTypeEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<ProcessTypeEntity>
    fun getById(id: UUID): ProcessTypeEntity?
}