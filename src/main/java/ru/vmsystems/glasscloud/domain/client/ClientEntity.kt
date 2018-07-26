package ru.vmsystems.glasscloud.domain.client

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "client_type", schema = "glass")
class ClientTypeEntity(
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
class ClientEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val inn: String,
        val account: String,
        val phone: String,
        val email: String,
        val description: String = "",
        val discount: String,
        @ManyToOne
        val clientType: ClientTypeEntity,
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
