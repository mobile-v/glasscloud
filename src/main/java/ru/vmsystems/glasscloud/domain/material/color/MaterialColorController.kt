package ru.vmsystems.glasscloud.domain.material.color

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.domain.material.MaterialColorDto
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/material/color")
class MaterialColorController @Autowired
constructor(private val materialColorService: MaterialColorService) {

    @GetMapping
    fun get(): JsonItemResponse<List<MaterialColorDto>> {
        return JsonItemBuilder.success(materialColorService.getAll())
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<MaterialColorDto> {
        return JsonItemBuilder.success(materialColorService.get(colorId))
    }

    @PostMapping
    fun save(@RequestBody color: MaterialColorDto): JsonItemResponse<*> {
        materialColorService.save(color)
        return JsonItemBuilder.success()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") colorId: UUID, @RequestBody color: MaterialColorDto): JsonItemResponse<*> {
        materialColorService.save(color.copy(id = colorId))
        return JsonItemBuilder.success()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<*> {
        materialColorService.delete(colorId)
        return JsonItemBuilder.success()
    }
}
