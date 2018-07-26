package ru.vmsystems.glasscloud.domain.company

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.SessionService
import java.security.Principal
import java.util.*

@RestController("ReceptionControllerApi")
@RequestMapping("/api/receptionOfOrder")
class ReceptionController(
        private val receptionService: ReceptionService,
        private val sessionService: SessionService
) {

    //http://localhost:8080/api/receptionOfOrder/current
    val currentReception: ResponseEntity<ReceptionDto>
        @GetMapping(value = ["/current"])
        get() {

            val currentReception = sessionService.currentReception
            return if (currentReception != null) {
                ResponseEntity(currentReception.transform(), HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.NO_CONTENT)
            }
        }

    //http://localhost:8080/api/receptionOfOrder/1
    @GetMapping(value = ["/{receptionOfOrderId}"])
    operator fun get(@PathVariable(value = "receptionOfOrderId") receptionOfOrderId: UUID): ResponseEntity<ReceptionDto> {

        val order = receptionService[receptionOfOrderId]
        return ResponseEntity(order, HttpStatus.OK)
    }

    //http://localhost:8080/api/receptionOfOrder/
    @PostMapping(value = [""])
    fun save(@RequestBody receptionOfOrder: ReceptionDto): ResponseEntity<ReceptionDto> {
        receptionService.create(receptionOfOrder)
        return ResponseEntity(receptionOfOrder, HttpStatus.OK)
    }

    @GetMapping(value = [""])
    fun getReceptions(principal: Principal): ResponseEntity<List<ReceptionDto>> {

        val result = receptionService.getByUserName(principal.name)

        return ResponseEntity(result, HttpStatus.OK)
    }

    @PostMapping(value = ["/select/{receptionOfOrderId}"])
    fun selectReceptionOfOrder(@PathVariable(value = "receptionOfOrderId") receptionOfOrderId: UUID): ResponseEntity<*> {

        sessionService.setReceptionOfOrder(receptionOfOrderId)

        return ResponseEntity<Any>(HttpStatus.OK)
    }
}
