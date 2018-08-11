package ru.vmsystems.glasscloud.domain.process

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/process")
class ProcessController @Autowired
constructor(private val service: ProcessService) {

    @GetMapping
    fun get(): JsonItemResponse<List<ProcessDto>> {
        return JsonItemBuilder.success(service.getAll())
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable(value = "id") id: UUID): JsonItemResponse<ProcessDto> {
        return JsonItemBuilder.success(service.get(id))
    }

    @PostMapping
    fun save(@RequestBody dto: ProcessDto): JsonItemResponse<*> {
        service.save(dto)
        return JsonItemBuilder.success()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") colorId: UUID, @RequestBody dto: ProcessDto): JsonItemResponse<*> {
        service.save(dto.copy(id = colorId))
        return JsonItemBuilder.success()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<*> {
        service.delete(colorId)
        return JsonItemBuilder.success()
    }
}
