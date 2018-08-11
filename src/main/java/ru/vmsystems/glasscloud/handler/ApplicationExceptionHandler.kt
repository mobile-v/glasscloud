package ru.vmsystems.glasscloud.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.vmsystems.glasscloud.getLogger
import ru.vmsystems.glasscloud.json.JsonItemBuilder
import ru.vmsystems.glasscloud.json.JsonItemResponse

@ControllerAdvice
class ApplicationExceptionHandler {

    companion object {
        private val LOG = getLogger()
    }

    @ExceptionHandler(value = [(BusinessException::class)])
    fun handleLdapException(ex: RuntimeException): ResponseEntity<JsonItemResponse<*>> {
        LOG.error(ex.message, ex)

        if (ex is BusinessException) {
            return ResponseEntity(JsonItemBuilder.error(ex), HttpStatus.OK)
        }

        throw ex
    }

    @ExceptionHandler
    fun handleException(exception: RuntimeException): ResponseEntity<*> {
        LOG.error(exception.message, exception)

        return if (exception.message != null) {
            ResponseEntity(exception.message!!, HttpStatus.OK)
        } else {
            ResponseEntity(exception.javaClass.simpleName, HttpStatus.OK)
        }
    }
}