package ru.zahv.alex.socialnetwork.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.web.filter.OncePerRequestFilter
import ru.zahv.alex.socialnetwork.logging.Headers.Companion.TRACE_ID
import java.io.IOException
import java.util.*


class CustomPerRequestFilter : OncePerRequestFilter(), Ordered {
    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        try {
            val requestTraceId = Optional.ofNullable(request.getHeader(TRACE_ID))
                    .orElseGet { UUID.randomUUID().toString() }
            TraceIdHolder.traceId = requestTraceId
            MDC.put(TRACE_ID, requestTraceId)
            response.addHeader(TRACE_ID, requestTraceId)
            filterChain.doFilter(request, response)
        } finally {
            TraceIdHolder.clear()
            MDC.clear()
        }
    }
}
