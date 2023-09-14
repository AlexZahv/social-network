package ru.zahv.alex.socialnetwork

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableCaching
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableFeignClients
class SocialNetworkApplication

fun main(args: Array<String>) {
    runApplication<SocialNetworkApplication>(*args)
}
