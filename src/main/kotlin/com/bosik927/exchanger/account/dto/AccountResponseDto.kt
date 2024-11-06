package com.bosik927.exchanger.account.dto

import com.bosik927.exchanger.currency.CurrencyCode
import java.math.BigDecimal
import java.util.UUID

data class AccountResponseDto(
    val uuid: UUID,
    val name: String,
    val surname: String,
    val balances: Map<CurrencyCode, BigDecimal>
)