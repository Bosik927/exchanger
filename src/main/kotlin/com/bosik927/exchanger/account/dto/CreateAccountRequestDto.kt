package com.bosik927.exchanger.account.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class CreateAccountRequestDto(
    @NotNull
    val name: String,
    @NotNull
    val surname: String,
    @NotNull @Positive
    val initialBalance: BigDecimal
)