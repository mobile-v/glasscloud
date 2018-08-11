package ru.vmsystems.glasscloud.domain.material.type

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.material.MaterialTypeDto
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/material/type")
class MaterialTypeController @Autowired
constructor(private val materialTypeService: MaterialTypeService) {

    @GetMapping
    fun get(): JsonItemResponse<List<MaterialTypeDto>> {
        return JsonItemBuilder.success(materialTypeService.getAll())
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable(value = "id") id: UUID): JsonItemResponse<MaterialTypeDto> {
        return JsonItemBuilder.success(materialTypeService.get(id))
    }

    @PostMapping
    fun save(@RequestBody dto: MaterialTypeDto): JsonItemResponse<*> {
        materialTypeService.save(dto)
        return JsonItemBuilder.success()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") colorId: UUID, @RequestBody dto: MaterialTypeDto): JsonItemResponse<*> {
        materialTypeService.save(dto.copy(id = colorId))
        return JsonItemBuilder.success()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<*> {
        materialTypeService.delete(colorId)
        return JsonItemBuilder.success()
    }
}
