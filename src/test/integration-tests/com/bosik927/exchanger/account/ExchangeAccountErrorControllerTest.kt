package com.bosik927.exchanger.account

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeAccountErrorControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should return NOT_FOUND for not exists account`() {
        val notExistAccountUuid = UUID.randomUUID()

        webTestClient.get()
            .uri("/api/accounts/$notExistAccountUuid")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }

    @Test
    fun `Should return BAD_REQUEST for not illegal uuid`() {
        val notExistAccountUuid = "NOT_LEGAL_UUID"

        webTestClient.get()
            .uri("/api/accounts/$notExistAccountUuid")
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }
}