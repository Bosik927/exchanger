package com.bosik927.exchanger.currency

import com.bosik927.exchanger.currency.CurrencyConversionRequestValidator.validateAmount
import com.bosik927.exchanger.currency.CurrencyConversionRequestValidator.validateDifferentCurrencies
import com.bosik927.exchanger.currency.dto.CurrencyConversionRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class CurrencyConversionController(
    private val currencyConversionService: CurrencyConversionService
) {

    @PostMapping("/api/convert/{accountUuid}")
    suspend fun convert(
        @PathVariable accountUuid: UUID,
        @RequestBody request: CurrencyConversionRequestDto
    ): ResponseEntity<Void> {
        validateDifferentCurrencies(request.sourceCurrency, request.targetCurrency)
        validateAmount(request.amount)
        currencyConversionService.convertCurrency(
            accountUuid,
            request.sourceCurrency,
            request.targetCurrency,
            request.amount
        )
        return ResponseEntity.noContent().build()
    }
}