package com.bosik927.exchanger.account

import com.bosik927.exchanger.common.error.AdviceControllerOrder
import com.bosik927.exchanger.common.error.ErrorDto
import com.bosik927.exchanger.account.exception.AccountNotFoundException
import com.bosik927.exchanger.account.exception.IncorrectAccountRequestException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
@Order(AdviceControllerOrder.MODULE)
class AccountErrorController {


    @ExceptionHandler(AccountNotFoundException::class)
    fun handleExchangeAccountNotFoundException(
        ex: AccountNotFoundException
    ): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(
            errorMessage = ex.message ?: "Account not found"
        )
        return ResponseEntity(errorDto, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IncorrectAccountRequestException::class)
    fun handleIncorrectAccountRequestException(
        ex: IncorrectAccountRequestException
    ): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(
            errorMessage = ex.message ?: "Incorrect request body."
        )
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }
}