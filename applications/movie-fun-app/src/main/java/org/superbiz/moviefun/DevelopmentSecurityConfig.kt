package org.superbiz.moviefun

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@Profile("development")
@Configuration
class DevelopmentSecurityConfig {

    @Bean
    @LoadBalanced
    fun restOperations(): RestOperations {
        return RestTemplate()
    }
}
