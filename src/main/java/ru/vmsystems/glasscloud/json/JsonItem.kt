package ru.vmsystems.glasscloud.json

import com.fasterxml.jackson.annotation.JsonInclude

enum class ResponseStatus(val status: String) {
    SUCCESS("success"),
    ERROR("error"),
}

@JsonInclude(JsonInclude.Include.NON_NULL)
open class JsonItem<T> {
    var data: T? = null
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class JsonItemResponse<T>(
        var status: ResponseStatus,
        var message: String? = null
) : JsonItem<T>() {
    fun getStatus(): String {
        return status.status
    }
}