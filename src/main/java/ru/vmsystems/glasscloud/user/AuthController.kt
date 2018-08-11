package ru.vmsystems.glasscloud.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vmsystems.glasscloud.json.JsonItemBuilder

@RestController
@RequestMapping("/api/is_auth")
class AuthController {

    @GetMapping()
    fun isAuth() = JsonItemBuilder.success(true)
}
