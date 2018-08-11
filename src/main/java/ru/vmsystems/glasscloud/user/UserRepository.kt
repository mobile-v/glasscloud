package ru.vmsystems.glasscloud.user


import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<UserEntity, UUID> {
    fun getById(id: UUID): UserEntity?
    fun getByLogin(login: String): UserEntity?
    fun getByCompanyId(companyId: UUID): List<UserEntity>
}
