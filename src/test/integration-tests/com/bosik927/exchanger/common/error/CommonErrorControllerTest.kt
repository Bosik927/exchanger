package com.bosik927.exchanger.common.error

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommonErrorControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should return BAD_REQUEST for not valid request body`() {
        val invalidRequestBody = """
            {
                "name": "",
                "surname": "",
                "initialBalance": null
            }
        """

        webTestClient.post()
            .uri("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errorMessage").exists() // Check if the error message is present
    }

    @Test
    fun `should return METHOD_NOT_ALLOWED for unsupported HTTP method`() {
        webTestClient.delete()
            .uri("/api/accounts/123")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.METHOD_NOT_ALLOWED)
            .expectBody()
            .jsonPath("$.errorMessage").isEqualTo("Method not allowed exception.")
    }

    @Test
    fun `should return NOT_FOUND for NoResourceFoundException`() {
        webTestClient.get()
            .uri("/api/non-existent-path")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.errorMessage").isEqualTo("Path not found.")
    }

    @Test
    fun `should return BAD_REQUEST for ServerWebInputException`() {
        val invalidRequestBody = "invalid"

        webTestClient.post()
            .uri("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequestBody)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errorMessage").isEqualTo("Incorrect request body.")
    }
}