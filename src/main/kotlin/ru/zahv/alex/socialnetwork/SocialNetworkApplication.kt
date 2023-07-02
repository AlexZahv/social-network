package ru.zahv.alex.socialnetwork

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class SocialNetworkApplication

fun main(args: Array<String>) {
    runApplication<SocialNetworkApplication>(*args)
}
