package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.dto.AccountResponseDto
import com.bosik927.exchanger.account.model.ExchangeAccount
import java.util.*

fun CreateAccountRequestDto.toEntity(): ExchangeAccount {
    return ExchangeAccount(
        uuid = UUID.randomUUID(),
        name = this.name,
        surname = this.surname,
        balance = this.initialBalance
    )
}

fun ExchangeAccount.toDto(): AccountResponseDto {
    return AccountResponseDto(
        uuid = this.uuid,
        name = this.name,
        surname = this.surname,
        balance = this.balance
    )
}