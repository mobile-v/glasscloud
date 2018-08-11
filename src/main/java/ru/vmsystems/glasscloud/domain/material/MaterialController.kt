package ru.vmsystems.glasscloud.domain.material

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/material")
class MaterialController @Autowired
constructor(private val materialColorService: MaterialService) {

    @GetMapping
    fun get(): JsonItemResponse<List<MaterialDto>> {
        return JsonItemBuilder.success(materialColorService.getAll())
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable(value = "id") id: UUID): JsonItemResponse<MaterialDto> {
        return JsonItemBuilder.success(materialColorService.get(id))
    }

    @PostMapping
    fun save(@RequestBody material: MaterialDto): JsonItemResponse<*> {
        materialColorService.save(material)
        return JsonItemBuilder.success()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") colorId: UUID, @RequestBody dto: MaterialDto): JsonItemResponse<*> {
        materialColorService.save(dto.copy(id = colorId))
        return JsonItemBuilder.success()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<*> {
        materialColorService.delete(colorId)
        return JsonItemBuilder.success()
    }
}
