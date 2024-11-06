package com.bosik927.exchanger.currency

import com.bosik927.exchanger.account.AccountService
import com.bosik927.exchanger.currency.client.NbpExchangeRateClient
import com.bosik927.exchanger.currency.exception.BalanceNotFoundException
import com.bosik927.exchanger.currency.exception.InsufficientBalanceException
import org.springframework.stereotype.Service
import java.math.RoundingMode
import java.math.BigDecimal
import java.util.*

@Service
class CurrencyConversionService(
    val accountService: AccountService,
    val nbpExchangeRateClient: NbpExchangeRateClient
) {

    companion object {
        const val ACTUAL_EXCHANGE_INDEX = 0
    }

    suspend fun convertCurrency(
        accountUuid: UUID,
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        amount: BigDecimal
    ) {
        val account = accountService.fetchOne(accountUuid)
        val balances = account.balances.toMutableMap()
        val plnBalance =
            account.balances[CurrencyCode.PLN] ?: throw BalanceNotFoundException(accountUuid, CurrencyCode.PLN)
        val usdBalance =
            account.balances[CurrencyCode.USD] ?: throw BalanceNotFoundException(accountUuid, CurrencyCode.USD)

        val usdRateExchange = nbpExchangeRateClient.getExchangeRate(CurrencyCode.USD).rates[ACTUAL_EXCHANGE_INDEX].mid
        when (sourceCurrency) {
            CurrencyCode.PLN -> {
                if (plnBalance < amount) throw InsufficientBalanceException(CurrencyCode.PLN, plnBalance, amount)
                balances[CurrencyCode.PLN] = plnBalance - amount
                balances[CurrencyCode.USD] = usdBalance + amount.divide(usdRateExchange, 6, RoundingMode.HALF_UP)
            }
            CurrencyCode.USD -> {
                if (usdBalance < amount) throw InsufficientBalanceException(CurrencyCode.USD, usdBalance, amount)
                balances[CurrencyCode.USD] = usdBalance - amount
                balances[CurrencyCode.PLN] = plnBalance + amount.multiply(usdRateExchange)
            }
        }

        accountService.update(accountUuid, account.copy(balances = balances))
    }
}