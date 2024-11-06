package com.bosik927.exchanger.currency

import com.bosik927.exchanger.currency.exception.IncorrectAmountException
import com.bosik927.exchanger.currency.exception.SameCurrencyConversionException
import java.math.BigDecimal

object CurrencyConversionRequestValidator {

    fun validateDifferentCurrencies(sourceCurrency: CurrencyCode, targetCurrency: CurrencyCode) {
        if (sourceCurrency == targetCurrency) {
            throw SameCurrencyConversionException()
        }
    }

    fun validateAmount(balance: BigDecimal) {
        if (balance < BigDecimal.ZERO) {
            throw IncorrectAmountException()
        }
    }
}