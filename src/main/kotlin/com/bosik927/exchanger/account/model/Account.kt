package com.bosik927.exchanger.account.model

import java.math.BigDecimal
import java.util.UUID

data class Account(
    val uuid: UUID,
    val name: String,
    val surname: String,
    val balance: BigDecimal
)