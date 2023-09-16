package ru.zahv.alex.socialnetwork.logging

import java.util.*


object TraceIdHolder {
    private val traceIdContainer = ThreadLocal<String>()
    val newTraceId: String
        get() {
            traceIdContainer.set(UUID.randomUUID().toString())
            return traceIdContainer.get()
        }
    var traceId: String?
        get() = traceIdContainer.get()
        set(traceId) {
            traceIdContainer.set(traceId)
        }

    fun clear() {
        traceIdContainer.remove()
    }
}

