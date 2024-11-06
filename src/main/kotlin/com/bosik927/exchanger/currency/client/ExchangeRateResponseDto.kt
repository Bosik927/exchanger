package com.bosik927.exchanger.currency.client

import java.math.BigDecimal

data class ExchangeRateResponseDto(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<Rate>
)

data class Rate(
    val no: String,
    val effectiveDate: String,
    val mid: BigDecimal
)