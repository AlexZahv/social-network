package ru.zahv.alex.socialnetwork.logging

import lombok.extern.slf4j.Slf4j
import net.logstash.logback.marker.Markers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.zalando.logbook.Correlation
import org.zalando.logbook.HttpRequest
import org.zalando.logbook.HttpResponse
import org.zalando.logbook.Precorrelation
import java.io.IOException
import java.nio.charset.StandardCharsets


@Slf4j
@Service
class HttpLoggerService {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class)
    fun logRequest(precorrelation: Precorrelation, request: HttpRequest) {
        val params = java.util.Map.of(
                "correlation", precorrelation.id,
                "uri", request.requestUri,
                "headers", request.headers,
                "method", request.method,
                "body", String(request.body, StandardCharsets.UTF_8))
        log(params, "REQUEST")
    }

    @Throws(IOException::class)
    fun logResponse(correlation: Correlation, response: HttpResponse) {
        val params = java.util.Map.of(
                "correlation", correlation.id,
                "executionTime", correlation.duration,
                "headers", response.headers,
                "status", response.status,
                "body", String(response.body, StandardCharsets.UTF_8))
        log(params, "RESPONSE")
    }

    @Throws(IOException::class)
    fun logAppCall(correlation: Correlation, request: HttpRequest, response: HttpResponse) {
        val requestParams = java.util.Map.of(
                "correlation", correlation.id,
                "uri", request.requestUri,
                "headers", request.headers,
                "method", request.method,
                "body", String(request.body, StandardCharsets.UTF_8))
        val responseParams = java.util.Map.of(
                "correlation", correlation.id,
                "executionTime", correlation.duration,
                "headers", response.headers,
                "status", response.status,
                "body", String(response.body, StandardCharsets.UTF_8))
        log.info(
                Markers.aggregate(
                        Markers.append("request", requestParams),
                        Markers.append("response", responseParams),
                        Markers.append(TYPE, "REQUEST/RESPONSE")
                ).toString())
    }

    private fun log(params: Map<String?, Any?>, facility: String) {
        val markers = Markers.aggregate(
                Markers.appendEntries(params),
                Markers.append(TYPE, facility)
        )
        log.info(markers.toString())
    }

    companion object {
        private const val TYPE = "type"
    }
}
