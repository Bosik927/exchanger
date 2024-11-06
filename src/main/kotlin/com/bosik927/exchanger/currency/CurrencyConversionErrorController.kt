package com.bosik927.exchanger.currency

import com.bosik927.exchanger.common.error.AdviceControllerOrder
import com.bosik927.exchanger.common.error.ErrorDto
import com.bosik927.exchanger.currency.exception.BalanceNotFoundException
import com.bosik927.exchanger.currency.exception.ExchangeRateServiceException
import com.bosik927.exchanger.currency.exception.IncorrectAmountException
import com.bosik927.exchanger.currency.exception.InsufficientBalanceException
import com.bosik927.exchanger.currency.exception.SameCurrencyConversionException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(AdviceControllerOrder.MODULE)
class CurrencyConversionErrorController {

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleInsufficientBalanceException(
        ex: InsufficientBalanceException
    ): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(errorMessage = ex.message ?: "")
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BalanceNotFoundException::class)
    fun handleBalanceNotFoundException(ex: BalanceNotFoundException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(errorMessage = ex.message ?: "")
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ExchangeRateServiceException::class)
    fun handleExchangeRateServiceException(ex: ExchangeRateServiceException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(errorMessage = ex.message ?: "")
        return ResponseEntity(errorDto, HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(SameCurrencyConversionException::class)
    fun handleSameCurrencyConversionException(ex: SameCurrencyConversionException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(errorMessage = ex.message ?: "")
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IncorrectAmountException::class)
    fun handleIncorrectAmountException(ex: IncorrectAmountException)
            : ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(errorMessage = ex.message ?: "")
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }
}