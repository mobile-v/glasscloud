package ru.vmsystems.glasscloud.domain.company

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity
@Table(name = "company", schema = "glass")
data class CompanyEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
