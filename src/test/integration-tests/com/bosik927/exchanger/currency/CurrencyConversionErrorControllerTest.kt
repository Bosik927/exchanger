package com.bosik927.exchanger.currency

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyConversionErrorControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient
    private lateinit var accountUuid: String

    @BeforeEach
    fun setUp() {
        webTestClient.post()
            .uri("/api/accounts")
            .header("Content-Type", "application/json")
            .bodyValue(
                """
                {
                    "name": "Name",
                    "surname": "Surname",
                    "initialPlnBalance": 1000
                }
            """
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.uuid").exists()
            .jsonPath("$.uuid").value<String> { uuid -> accountUuid = uuid }
    }

    @Test
    fun `Should return BAD_REQUEST for InsufficientBalanceException`() {
        val sourceCurrency = "USD"
        val targetCurrency = "EUR"
        val amount = BigDecimal("1000.00")

        webTestClient.post()
            .uri("/api/convert/$accountUuid")
            .bodyValue(
                mapOf(
                    "sourceCurrency" to sourceCurrency,
                    "targetCurrency" to targetCurrency,
                    "amount" to amount.toString()
                )
            )
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }

    @Test
    fun `Should return BAD_REQUEST for INVALID_CURRENCY`() {
        val accountUuid = UUID.randomUUID()
        val sourceCurrency = "USD"
        val targetCurrency = "INVALID_CURRENCY"
        val amount = BigDecimal("100.00")

        webTestClient.post()
            .uri("/api/convert/$accountUuid")
            .bodyValue(
                mapOf(
                    "sourceCurrency" to sourceCurrency,
                    "targetCurrency" to targetCurrency,
                    "amount" to amount.toString()
                )
            )
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }

    @Test
    fun `Should return BAD_REQUEST for the same source and target currency`() {
        val accountUuid = UUID.randomUUID()
        val sourceCurrency = "THE_SAME_CURRENCY"
        val targetCurrency = "THE_SAME_CURRENCY"
        val amount = BigDecimal("100.00")

        webTestClient.post()
            .uri("/api/convert/$accountUuid")
            .bodyValue(
                mapOf(
                    "sourceCurrency" to sourceCurrency,
                    "targetCurrency" to targetCurrency,
                    "amount" to amount.toString()
                )
            )
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }

    @Test
    fun `Should return BAD_REQUEST for not positive amount`() {
        val accountUuid = UUID.randomUUID()
        val sourceCurrency = "PLN"
        val targetCurrency = "USD"
        val amount = BigDecimal("-100.00")

        webTestClient.post()
            .uri("/api/convert/$accountUuid")
            .bodyValue(
                mapOf(
                    "sourceCurrency" to sourceCurrency,
                    "targetCurrency" to targetCurrency,
                    "amount" to amount.toString()
                )
            )
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }
}