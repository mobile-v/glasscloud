package ru.vmsystems.glasscloud.domain.company


import org.springframework.data.repository.CrudRepository

interface CompanyRepository : CrudRepository<CompanyEntity, Long>

interface ReceptionRepository : CrudRepository<ReceptionEntity, Long> {
    fun getByCompanyId(companyId: Long?): List<ReceptionEntity>
}
