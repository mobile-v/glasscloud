package ru.vmsystems.glasscloud.domain.client

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "client_type", schema = "glass")
data class ClientTypeEntity(
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

@Entity
@Table(name = "client", schema = "glass")
data class ClientEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val inn: Long,
        val account: String,
        val phone: String,
        val email: String,
        val description: String?,
        val discount: Float,
        @ManyToOne
        val clientType: ClientTypeEntity,
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
