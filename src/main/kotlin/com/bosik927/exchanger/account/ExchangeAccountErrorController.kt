package com.bosik927.exchanger.account

import com.bosik927.exchanger.common.error.AdviceControllerOrder
import com.bosik927.exchanger.common.error.ErrorDto
import com.bosik927.exchanger.account.exception.AccountNotFoundException
import com.bosik927.exchanger.account.exception.IllegalUuidException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
@Order(AdviceControllerOrder.SPECIFIC)
class ExchangeAccountErrorController {

    @ExceptionHandler(IllegalUuidException::class)
    fun handleIllegalUuidException(ex: IllegalUuidException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(errorMessage = ex.message ?: "")
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleExchangeAccountNotFoundException(ex: AccountNotFoundException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(
            errorMessage = ex.message ?: "Account not found"
        )
        return ResponseEntity(errorDto, HttpStatus.NOT_FOUND)
    }
}