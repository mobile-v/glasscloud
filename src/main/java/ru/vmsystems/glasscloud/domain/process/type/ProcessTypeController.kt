package ru.vmsystems.glasscloud.domain.process.type

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.process.ProcessTypeDto
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/process/type")
class ProcessTypeController @Autowired
constructor(private val service: ProcessTypeService) {

    @GetMapping
    fun get(): JsonItemResponse<List<ProcessTypeDto>> {
        return JsonItemBuilder.success(service.getAll())
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable(value = "id") id: UUID): JsonItemResponse<ProcessTypeDto> {
        return JsonItemBuilder.success(service.get(id))
    }

    @PostMapping
    fun save(@RequestBody dto: ProcessTypeDto): JsonItemResponse<*> {
        service.save(dto)
        return JsonItemBuilder.success()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") colorId: UUID, @RequestBody dto: ProcessTypeDto): JsonItemResponse<*> {
        service.save(dto.copy(id = colorId))
        return JsonItemBuilder.success()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<*> {
        service.delete(colorId)
        return JsonItemBuilder.success()
    }
}
