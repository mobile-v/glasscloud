package ru.vmsystems.glasscloud.domain

import org.springframework.stereotype.Service
import ru.vmsystems.glasscloud.domain.company.ReceptionEntity
import ru.vmsystems.glasscloud.domain.company.ReceptionRepository
import ru.vmsystems.glasscloud.user.UserRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpServletRequest

@Service
class SessionService(
        private val userRepository: UserRepository,
        private val receptionRepository: ReceptionRepository,
        private val httpRequest: HttpServletRequest) {

    private val receptions = ConcurrentHashMap<String, UUID>()

    val receptionOfOrder: UUID?
        get() = receptions[httpRequest.session.id]

    val currentReceptionOfOrderName: String
        get() {
            val id = receptions[httpRequest.session.id] ?: return ""
            val entity = receptionRepository.getById(id) ?: return ""
            return entity.name
        }

    val currentReception: ReceptionEntity
        get() {
            val id = receptions[httpRequest.session.id] ?: throw RuntimeException("Не выбрана точка приема заказов")

            return receptionRepository.getById(id) ?: throw RuntimeException("Не найдена точка приема заказов")
        }

    val currentCompanyId: UUID
        get() {
            return currentReception.companyId
        }

    val currentReceptionId: UUID
        get() {
            return currentReception.id!!
        }

    fun setReceptionOfOrder(reception: UUID) {
        val user = userRepository.getByLogin(httpRequest.remoteUser)
                ?: throw RuntimeException("Пользователь не найден")

        receptionRepository.getByCompanyId(user.companyId!!)
                .find { it.id == reception } ?: RuntimeException("Точка приема не найдена")

        receptions[httpRequest.session.id] = reception
    }
}
