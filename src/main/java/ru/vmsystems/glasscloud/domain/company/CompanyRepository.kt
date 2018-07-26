package ru.vmsystems.glasscloud.domain.company


import org.springframework.data.repository.CrudRepository
import java.util.*

interface CompanyRepository : CrudRepository<CompanyEntity, UUID> {
    fun getById(id: UUID): CompanyEntity?
}

interface ReceptionRepository : CrudRepository<ReceptionEntity, UUID> {
    fun getByCompanyId(companyId: UUID): List<ReceptionEntity>
    fun getById(id: UUID): ReceptionEntity?
}
