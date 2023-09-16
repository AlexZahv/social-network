package ru.zahv.alex.socialnetwork.logging

import feign.Feign
import feign.Logger
import org.springframework.cloud.openfeign.FeignBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.Logbook
import org.zalando.logbook.Sink
import org.zalando.logbook.openfeign.FeignLogbookLogger


@Configuration
class LoggerConfiguration {
    @Bean
    fun customSink(loggingService: HttpLoggerService): Sink {
        return CustomSink(loggingService)
    }

    @Bean
    fun customPerRequestFilter(): CustomPerRequestFilter {
        return CustomPerRequestFilter()
    }

    @Bean
    fun feignBuilderCustomizer(
            logbook: Logbook?,
    ): FeignBuilderCustomizer {
        return FeignBuilderCustomizer { builder: Feign.Builder ->
            builder.requestInterceptor(
                    TraceIdRequestInterceptor()
            )
                    .logger(FeignLogbookLogger(logbook))
                    .logLevel(Logger.Level.FULL)
        }
    }
}

