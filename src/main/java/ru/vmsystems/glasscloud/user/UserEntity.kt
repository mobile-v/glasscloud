package ru.vmsystems.glasscloud.user

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "user", schema = "glass")
data class UserEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val login: String,
        val password: String,
        val enabled: Boolean,
        val role: String,
        val email: String,
        val phone: String,
        val comment: String?,
        val companyId: UUID?,
        val deleted: Boolean = false
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
