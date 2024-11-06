package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.dto.AccountResponseDto
import com.bosik927.exchanger.account.model.Account
import com.bosik927.exchanger.currency.CurrencyCode
import java.math.BigDecimal
import java.util.HashMap
import java.util.UUID

fun CreateAccountRequestDto.toEntity(): Account {
    val balances = HashMap<CurrencyCode, BigDecimal>()
    balances.put(CurrencyCode.PLN, this.initialPlnBalance)
    balances.put(CurrencyCode.USD, BigDecimal.ZERO)
    return Account(
        uuid = UUID.randomUUID(),
        name = this.name,
        surname = this.surname,
        balances = balances
    )
}

fun Account.toResponseDto(): AccountResponseDto {
    return AccountResponseDto(
        uuid = this.uuid,
        name = this.name,
        surname = this.surname,
        balances = this.balances
    )
}