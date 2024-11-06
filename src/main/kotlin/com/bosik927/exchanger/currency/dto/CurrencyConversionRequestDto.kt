package com.bosik927.exchanger.currency.dto

import com.bosik927.exchanger.currency.CurrencyCode
import java.math.BigDecimal

data class CurrencyConversionRequestDto(
    val sourceCurrency: CurrencyCode,
    val targetCurrency: CurrencyCode,
    val amount: BigDecimal
)