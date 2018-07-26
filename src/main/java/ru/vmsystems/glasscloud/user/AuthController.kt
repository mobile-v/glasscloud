package ru.vmsystems.glasscloud.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/is_auth")
class AuthController {

    //http://localhost:8080/api/is_auth
    val isAuth: ResponseEntity<Boolean>
        @GetMapping()
        get() = ResponseEntity(true, HttpStatus.OK)
}
