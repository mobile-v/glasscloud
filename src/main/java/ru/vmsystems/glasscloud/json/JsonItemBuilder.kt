package ru.vmsystems.glasscloud.json

import ru.vmsystems.glasscloud.handler.BusinessException

class JsonItemBuilder {
    companion object {
        fun <T> success(data: T): JsonItemResponse<T> {
            val jsonItem = JsonItemResponse<T>(ResponseStatus.SUCCESS)
            jsonItem.data = data

            return jsonItem
        }

        fun success(): JsonItemResponse<*> {
            val jsonItem = JsonItemResponse<Any>(ResponseStatus.SUCCESS)

            return jsonItem
        }

        fun error(exception: BusinessException): JsonItemResponse<*> {
            return JsonItemResponse<Any>(ResponseStatus.ERROR, exception.message)
        }

        fun error(message: String): JsonItemResponse<*> {
            val jsonItem = JsonItemResponse<Any>(ResponseStatus.ERROR, message)
            return jsonItem
        }
    }
}
