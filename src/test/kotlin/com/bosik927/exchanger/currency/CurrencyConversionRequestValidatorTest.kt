package com.bosik927.exchanger.currency

import com.bosik927.exchanger.currency.exception.IncorrectAmountException
import com.bosik927.exchanger.currency.exception.SameCurrencyConversionException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal


class CurrencyConversionRequestValidatorTest {

    @Test
    fun `should throw SameCurrencyConversionException when source and target currencies are the same`() {
        val sourceCurrency = CurrencyCode.USD
        val targetCurrency = CurrencyCode.USD

        assertThrows<SameCurrencyConversionException> {
            CurrencyConversionRequestValidator.validateDifferentCurrencies(sourceCurrency, targetCurrency)
        }
    }

    @Test
    fun `should not throw exception when source and target currencies are different`() {
        val sourceCurrency = CurrencyCode.USD
        val targetCurrency = CurrencyCode.PLN

        CurrencyConversionRequestValidator.validateDifferentCurrencies(sourceCurrency, targetCurrency)
    }

    @Test
    fun `should not throw exception when correct amount`() {
        val correctAmount = BigDecimal.valueOf(100L)
        CurrencyConversionRequestValidator.validateAmount(correctAmount)
    }

    @Test
    fun `should throw exception when incorrect amount`() {
        val incorrectAmount = BigDecimal.valueOf(-100L)
        assertThrows<IncorrectAmountException> {
            CurrencyConversionRequestValidator.validateAmount(incorrectAmount)
        }
    }
}