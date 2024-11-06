package com.bosik927.exchanger.currency.exception

import com.bosik927.exchanger.currency.CurrencyCode
import java.util.UUID

class BalanceNotFoundException(accountUuid: UUID, currencyCode: CurrencyCode) :
    RuntimeException("Illegal currency [$currencyCode] for account uuid [$accountUuid]")
