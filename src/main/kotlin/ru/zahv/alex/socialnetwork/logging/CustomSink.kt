package ru.zahv.alex.socialnetwork.logging

import lombok.RequiredArgsConstructor
import org.zalando.logbook.*
import java.io.IOException


@RequiredArgsConstructor
class CustomSink(private val loggingService: HttpLoggerService) : Sink {

    @Throws(IOException::class)
    override fun write(precorrelation: Precorrelation, request: HttpRequest) {
        loggingService.logRequest(precorrelation, request)
    }

    @Throws(IOException::class)
    override fun write(correlation: Correlation, request: HttpRequest, response: HttpResponse) {
        loggingService.logResponse(correlation, response)
    }

    @Throws(IOException::class)
    override fun writeBoth(correlation: Correlation, request: HttpRequest, response: HttpResponse) {
        loggingService.logAppCall(correlation, request, response)
    }
}

