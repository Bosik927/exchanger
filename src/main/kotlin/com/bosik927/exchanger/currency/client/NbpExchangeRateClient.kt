package com.bosik927.exchanger.currency.client

import com.bosik927.exchanger.common.log
import com.bosik927.exchanger.currency.CurrencyCode
import com.bosik927.exchanger.currency.exception.ExchangeRateServiceException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody

@Component
class NbpExchangeRateClient(private val nbpWebClientBuilder: WebClient.Builder) {

    private val nbpWebClient = nbpWebClientBuilder.build()

    suspend fun getExchangeRate(currencyCode: CurrencyCode): ExchangeRateResponseDto {
        return try {
            nbpWebClient
                .get()
                .uri("/exchangerates/rates/A/$currencyCode")
                .header("Content-Type", "application/json")
                .retrieve()
                .awaitBody()
        } catch (ex: WebClientResponseException) {
            log.info("WebClientResponseException for currencyCode $currencyCode: ${ex.message}")
            throw ExchangeRateServiceException(currencyCode)
        }
    }
}