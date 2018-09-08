package ru.vmsystems.glasscloud.domain.client

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/client")
class ClientController(private val clientService: ClientService) {

    @ApiOperation(value = "Получить список всех клиентов")
    @GetMapping(value = ["type"])
    fun getAll() = JsonItemBuilder.success(clientService.clientTypes)

    @ApiOperation(value = "Получить список всех клиентов")
    @GetMapping(value = [""])
    fun get(): JsonItemResponse<List<ClientDto>> {
        val result = clientService.get()
        return JsonItemBuilder.success(result)
    }

    @ApiOperation(value = "Получить клиента по id клиента")
    @GetMapping(value = ["/{clientId}"])
    operator fun get(@PathVariable(value = "clientId") clientId: UUID): JsonItemResponse<ClientDto> {
        val client = clientService[clientId]
        return JsonItemBuilder.success(client)
    }

    @ApiOperation(value = "Создать нового клиента")
    @PostMapping(value = [""])
    fun save(@RequestBody client: ClientDto): JsonItemResponse<ClientDto> {
        return JsonItemBuilder.success(clientService.create(client))
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable(value = "id") clientId: UUID,
               @RequestBody client: ClientDto): JsonItemResponse<ClientDto> {
        return JsonItemBuilder.success(clientService.update(clientId, client))
    }

    @ApiOperation(value = "Удалить клиента по id клиента")
    @DeleteMapping(value = ["/{clientId}"])
    fun delete(@PathVariable(value = "clientId") clientId: UUID): JsonItemResponse<*> {
        clientService.delete(clientId)
        return JsonItemBuilder.success("OK")
    }
}
