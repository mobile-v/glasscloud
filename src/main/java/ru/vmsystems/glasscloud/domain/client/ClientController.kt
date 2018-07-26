package ru.vmsystems.glasscloud.domain.client

import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController("ClientControllerApi")
@RequestMapping("/api/client")
class ClientController(private val clientService: ClientService) {

    val clientTypes: ResponseEntity<List<ClientTypeDto>>
        @ApiOperation(value = "Получить список всех клиентов")
        @GetMapping(value = ["type"])
        get() = ResponseEntity(clientService.clientTypes, HttpStatus.OK)

    @ApiOperation(value = "Получить список всех клиентов")
    @GetMapping(value = [""])
    fun get(): ResponseEntity<List<ClientDto>> {
        val result = clientService.get()
        return ResponseEntity(result, HttpStatus.OK)
    }

    @ApiOperation(value = "Получить клиента по id клиента")
    @GetMapping(value = ["/{clientId}"])
    operator fun get(@PathVariable(value = "clientId") clientId: UUID): ResponseEntity<ClientDto> {

        val order = clientService[clientId]
        return ResponseEntity(order, HttpStatus.OK)
    }

    @ApiOperation(value = "Создать нового клиента")
    @PostMapping(value = [""])
    fun save(@RequestBody client: ClientDto): ResponseEntity<ClientDto> {
        return ResponseEntity(clientService.create(client), HttpStatus.OK)
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable(value = "id") clientId: UUID,
               @RequestBody client: ClientDto): ResponseEntity<ClientDto> {
        return ResponseEntity(clientService.update(clientId, client), HttpStatus.OK)
    }

    @ApiOperation(value = "Удалить клиента по id клиента")
    @DeleteMapping(value = ["/{clientId}"])
    fun delete(@PathVariable(value = "clientId") clientId: UUID): ResponseEntity<*> {
        clientService.delete(clientId)
        return ResponseEntity<Any>("OK", HttpStatus.OK)
    }
}
