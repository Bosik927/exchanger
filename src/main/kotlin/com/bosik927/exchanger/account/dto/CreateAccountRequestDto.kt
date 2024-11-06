package com.bosik927.exchanger.account.dto

import java.math.BigDecimal

data class CreateAccountRequestDto(
    val name: String,
    val surname: String,
    val initialPlnBalance: BigDecimal
)