package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.dto.AccountResponseDto
import com.bosik927.exchanger.account.model.Account
import java.util.UUID

fun CreateAccountRequestDto.toEntity(): Account {
    return Account(
        uuid = UUID.randomUUID(),
        name = this.name,
        surname = this.surname,
        balance = this.initialBalance
    )
}

fun Account.toResponseDto(): AccountResponseDto {
    return AccountResponseDto(
        uuid = this.uuid,
        name = this.name,
        surname = this.surname,
        balance = this.balance
    )
}