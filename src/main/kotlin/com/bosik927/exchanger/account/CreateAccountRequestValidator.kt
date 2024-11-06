package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.exception.IncorrectAccountRequestException
import java.math.BigDecimal

object CreateAccountRequestValidator {

    fun validate(request: CreateAccountRequestDto) {
        if (request.name.isBlank()) {
            throw IncorrectAccountRequestException("Name must not be blank")
        }
        if (request.surname.isBlank()) {
            throw IncorrectAccountRequestException("Surname must not be blank")
        }
        if (request.initialPlnBalance <= BigDecimal.ZERO) {
            throw IncorrectAccountRequestException("Initial pln balance must be positive")
        }
    }
}