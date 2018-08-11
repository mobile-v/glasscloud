package ru.vmsystems.glasscloud.domain

import org.springframework.beans.factory.annotation.Autowired
import ru.vmsystems.glasscloud.domain.company.CompanyEntity
import ru.vmsystems.glasscloud.domain.company.CompanyRepository
import ru.vmsystems.glasscloud.domain.company.ReceptionService
import ru.vmsystems.glasscloud.user.UserRepository
import java.util.*
import javax.servlet.http.HttpServletRequest

abstract class BackService {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var companyRepository: CompanyRepository
    @Autowired
    private lateinit var receptionService: ReceptionService
    @Autowired
    private lateinit var sessionService: SessionService

    @Autowired
    private val httpRequest: HttpServletRequest? = null

    val sesionId: String
        get() = httpRequest!!.session.id

    val login: String
        get() = httpRequest!!.remoteUser

    val company: CompanyEntity
        get() {
            val user = userRepository.getByLogin(login) ?: throw RuntimeException("user not found $login")
            return companyRepository.getById(user.companyId!!) ?: throw RuntimeException("company not found $companyId")
        }

    val companyId: UUID
        get() = company.id!!

    val receptionName: String
        get() = sessionService.currentReceptionOfOrderName
}
