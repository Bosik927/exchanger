package com.bosik927.exchanger.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun nbpWebClient(): WebClient.Builder {
        return WebClient.builder()
            .baseUrl("https://api.nbp.pl/api")
            .defaultHeader("Content-Type", "application/json")
    }
}