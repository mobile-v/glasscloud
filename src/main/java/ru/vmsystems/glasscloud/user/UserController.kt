package ru.vmsystems.glasscloud.user

import org.springframework.web.bind.annotation.*
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(private val service: UserService) {

    @GetMapping
    fun get(): JsonItemResponse<List<UserDto>> {
        return JsonItemBuilder.success(service.getAll())
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable(value = "id") id: UUID): JsonItemResponse<UserDto> {
        return JsonItemBuilder.success(service.get(id))
    }

    @PostMapping
    fun save(@RequestBody dto: UserDto): JsonItemResponse<*> {
        service.save(dto)
        return JsonItemBuilder.success()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable(value = "id") colorId: UUID, @RequestBody dto: UserDto): JsonItemResponse<*> {
        service.save(dto.copy(id = colorId))
        return JsonItemBuilder.success()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(value = "id") colorId: UUID): JsonItemResponse<*> {
        service.delete(colorId)
        return JsonItemBuilder.success()
    }
}
