package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.exception.IncorrectAccountRequestException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class CreateAccountRequestValidatorTest {

    @Test
    fun `should pass validation for valid input`() {
        val validRequest = CreateAccountRequestDto(
            name = "John",
            surname = "Doe",
            initialPlnBalance = BigDecimal("1000")
        )

        assertDoesNotThrow {
            CreateAccountRequestValidator.validate(validRequest)
        }
    }

    @Test
    fun `should throw exception for blank name`() {
        val invalidRequest = CreateAccountRequestDto(
            name = "",
            surname = "Doe",
            initialPlnBalance = BigDecimal("1000")
        )

        val exception = assertThrows<IncorrectAccountRequestException> {
            CreateAccountRequestValidator.validate(invalidRequest)
        }
        assertTrue(exception.message == "Name must not be blank")
    }

    @Test
    fun `should throw exception for blank surname`() {
        val invalidRequest = CreateAccountRequestDto(
            name = "John",
            surname = "",
            initialPlnBalance = BigDecimal("1000")
        )

        val exception = assertThrows<IncorrectAccountRequestException> {
            CreateAccountRequestValidator.validate(invalidRequest)
        }
        assertTrue(exception.message == "Surname must not be blank")
    }

    @Test
    fun `should throw exception for non-positive balance`() {
        val invalidRequest = CreateAccountRequestDto(
            name = "John",
            surname = "Doe",
            initialPlnBalance = BigDecimal("0")
        )

        val exception = assertThrows<IncorrectAccountRequestException> {
            CreateAccountRequestValidator.validate(invalidRequest)
        }
        assertTrue(exception.message == "Initial pln balance must be positive")
    }

    @Test
    fun `should throw exception for negative balance`() {
        val invalidRequest = CreateAccountRequestDto(
            name = "John",
            surname = "Doe",
            initialPlnBalance = BigDecimal("-100")
        )

        val exception = assertThrows<IncorrectAccountRequestException> {
            CreateAccountRequestValidator.validate(invalidRequest)
        }
        assertTrue(exception.message == "Initial pln balance must be positive")
    }
}