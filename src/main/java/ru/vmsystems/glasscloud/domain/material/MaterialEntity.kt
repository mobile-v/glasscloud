package ru.vmsystems.glasscloud.domain.material

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "material_color")
data class MaterialColorEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}

@Entity
@Table(name = "material_type")
data class MaterialTypeEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}

@Entity
@Table(name = "material")
data class MaterialEntity(
        @Id
        var id: UUID? = null,
        val name: String,
        val deleted: Boolean,
        val depth: Float,
        val length: Float,
        val width: Float,
        val price: BigDecimal,
        val description: String = "",
        @ManyToOne
        val color: MaterialColorEntity,
        @ManyToOne
        val type: MaterialTypeEntity,
        val companyId: UUID
) {
    @PrePersist
    private fun prePersist() {
        id = UUID.randomUUID()
    }
}
