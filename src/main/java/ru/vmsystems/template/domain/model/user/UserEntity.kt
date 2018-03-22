package ru.vmsystems.template.domain.model.user

import ru.vmsystems.template.domain.model.CompanyEntity
import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String? = null,
        var login: String? = null,
        var password: String? = null,
        var isEnabled: Boolean = false,
        var role: String? = null,
        @ManyToOne
        var company: CompanyEntity? = null
)