package ru.vmsystems.glasscloud

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

class Util {
    companion object {
        fun createCurrentTime(): Long {
            return Instant.now().toEpochMilli()
        }
    }
}

internal fun <C : Any> C.getLogger(): Logger =
        LoggerFactory.getLogger(this::class.java.name.substringBefore("\$Companion"))

fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) +  start