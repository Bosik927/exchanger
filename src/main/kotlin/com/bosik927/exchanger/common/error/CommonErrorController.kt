package com.bosik927.exchanger.common.error

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
@Order(AdviceControllerOrder.COMMON)
class CommonErrorController {

    @ExceptionHandler(MethodNotAllowedException::class)
    suspend fun handleMethodNotAllowedException(
        ex: MethodNotAllowedException
    ): ResponseEntity<ErrorDto> {
        val error = ErrorDto(
            errorMessage = "Method not allowed exception."
        )
        return ResponseEntity(error, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(
        ex: NoResourceFoundException
    ): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(
            errorMessage = "Path not found."
        )
        return ResponseEntity(errorDto, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleServerWebInputException(
        ex: ServerWebInputException
    ): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(
            errorMessage = "Incorrect request body."
        )
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    suspend fun handleUnknownException(
        ex: Exception
    ): ResponseEntity<ErrorDto> {
        val error = ErrorDto(
            errorMessage = "Unknown server error."
        )
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}