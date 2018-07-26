package ru.vmsystems.glasscloud.domain.process


import org.springframework.data.repository.CrudRepository
import ru.vmsystems.glasscloud.domain.company.CompanyEntity

interface ProcessRepository : CrudRepository<ProcessEntity, Long> {
    fun getByCompanyId(companyId: Long?): List<ProcessEntity>
    fun getByCompany(company: CompanyEntity): List<ProcessEntity>
}

interface ProcessTypeRepository : CrudRepository<ProcessTypeEntity, Long> {
    fun getByCompanyId(companyId: Long?): List<ProcessTypeEntity>
    fun getByCompany(company: CompanyEntity): List<ProcessTypeEntity>
}