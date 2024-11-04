package com.bosik927.exchanger.account.dto

import java.math.BigDecimal
import java.util.UUID

data class AccountResponseDto(
    val uuid: UUID,
    val name: String,
    val surname: String,
    val balance: BigDecimal
)