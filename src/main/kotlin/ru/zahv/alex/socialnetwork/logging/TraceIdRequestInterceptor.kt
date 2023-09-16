package ru.zahv.alex.socialnetwork.logging

import feign.RequestInterceptor
import feign.RequestTemplate
import ru.zahv.alex.socialnetwork.logging.Headers.Companion.TRACE_ID


class TraceIdRequestInterceptor : RequestInterceptor {
    override fun apply(requestTemplate: RequestTemplate) {
        var traceId = TraceIdHolder.traceId
        if (traceId.isNullOrEmpty()) {
            traceId = TraceIdHolder.newTraceId
        }
        requestTemplate.header(TRACE_ID, traceId)
    }
}
