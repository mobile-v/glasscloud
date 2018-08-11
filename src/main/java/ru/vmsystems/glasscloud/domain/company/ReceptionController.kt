package ru.vmsystems.glasscloud.domain.company

import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.SessionService
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.security.Principal
import java.util.*

@RestController("ReceptionControllerApi")
@RequestMapping("/api/receptionOfOrder")
class ReceptionController(
        private val receptionService: ReceptionService,
        private val sessionService: SessionService
) {

    @GetMapping(value = ["/current"])
    fun getCurrentReception(): JsonItemResponse<ReceptionDto> {
        val currentReception = sessionService.currentReception
        return JsonItemBuilder.success(currentReception.transform())
    }

    @GetMapping(value = ["/{receptionOfOrderId}"])
    operator fun get(@PathVariable(value = "receptionOfOrderId") receptionOfOrderId: UUID): JsonItemResponse<ReceptionDto> {

        val order = receptionService[receptionOfOrderId]
        return JsonItemBuilder.success(order)
    }

    @PostMapping(value = [""])
    fun save(@RequestBody receptionOfOrder: ReceptionDto): JsonItemResponse<ReceptionDto> {
        receptionService.create(receptionOfOrder)
        return JsonItemBuilder.success(receptionOfOrder)
    }

    @GetMapping(value = [""])
    fun getReceptions(principal: Principal): JsonItemResponse<List<ReceptionDto>> {
        val result = receptionService.getByUserName(principal.name)
        return JsonItemBuilder.success(result)
    }

    @PostMapping(value = ["/select/{receptionOfOrderId}"])
    fun selectReceptionOfOrder(@PathVariable(value = "receptionOfOrderId") receptionOfOrderId: UUID): JsonItemResponse<*> {
        sessionService.setReceptionOfOrder(receptionOfOrderId)
        return JsonItemBuilder.success()
    }
}
