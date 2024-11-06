package com.bosik927.exchanger.account

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountErrorControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `get should return NOT_FOUND for not exists account`() {
        val notExistAccountUuid = UUID.randomUUID()
        webTestClient.get()
            .uri("/api/accounts/$notExistAccountUuid")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }

    @Test
    fun `get should return BAD_REQUEST for not illegal uuid`() {
        val notExistAccountUuid = "NOT_LEGAL_UUID"

        webTestClient.get()
            .uri("/api/accounts/$notExistAccountUuid")
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }

    @Test
    fun `create should return BAD_REQUEST for not valid request`() {
        val invalidRequestBody = """
            {
                "name": "ValidName",
                "surname": "ValidName",
                "initialPlnBalance": -100
            }
        """

        webTestClient.post()
            .uri("/api/accounts")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errorMessage").exists()
    }
}