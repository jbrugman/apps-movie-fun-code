package org.superbiz.moviefun.movies

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
object MovieServiceApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(MovieServiceApplication::class.java, *args)
    }
}
