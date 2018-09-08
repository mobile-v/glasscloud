package ru.vmsystems.glasscloud.domain.company

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "reception", schema = "glass")
data class ReceptionEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val orderNumPrefix: String,
        val description: String?,
        val address: String,
        val phone: String,
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
