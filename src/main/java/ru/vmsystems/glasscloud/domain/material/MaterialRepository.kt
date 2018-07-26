package ru.vmsystems.glasscloud.domain.material


import org.springframework.data.repository.CrudRepository
import ru.vmsystems.glasscloud.domain.company.CompanyEntity

interface MaterialRepository : CrudRepository<MaterialEntity, Long> {
    fun getByCompanyId(companyId: Long?): List<MaterialEntity>
    fun getByCompany(company: CompanyEntity): List<MaterialEntity>
}


interface MaterialTypeRepository : CrudRepository<MaterialTypeEntity, Long> {
    fun getByCompanyId(companyId: Long?): List<MaterialTypeEntity>
    fun getByCompany(company: CompanyEntity): List<MaterialTypeEntity>
}


interface MaterialColorRepository : CrudRepository<MaterialColorEntity, Long> {
    fun getByCompanyId(companyId: Long?): List<MaterialColorEntity>
    fun getByCompany(company: CompanyEntity): List<MaterialColorEntity>
}