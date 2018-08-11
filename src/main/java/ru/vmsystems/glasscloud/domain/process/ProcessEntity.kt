package ru.vmsystems.glasscloud.domain.process

import ru.vmsystems.glasscloud.domain.material.MaterialEntity
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "process_type", schema = "glass")
data class ProcessTypeEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val description: String = "",
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}

@Entity
@Table(name = "process", schema = "glass")
data class ProcessEntity(
        @Id
        var id: UUID? = null,
        val deleted: Boolean,
        val description: String = "",
        val depth: Float,
        val price: BigDecimal,
        @ManyToOne
        val type: ProcessTypeEntity,
        val companyId: UUID,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "PROCESS_MATERIAL",
                joinColumns = (arrayOf(JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID"))),
                inverseJoinColumns = (arrayOf(JoinColumn(name = "MATERIAL_ID", referencedColumnName = "ID"))))
        val material: List<MaterialEntity>
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}

