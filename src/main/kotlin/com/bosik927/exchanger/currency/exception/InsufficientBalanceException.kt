package com.bosik927.exchanger.currency.exception

import com.bosik927.exchanger.currency.CurrencyCode
import java.math.BigDecimal

class InsufficientBalanceException(
    currency: CurrencyCode,
    currentBalance: BigDecimal,
    attemptedAmount: BigDecimal
) : RuntimeException("Insufficient balance in $currency: attempted $attemptedAmount, but balance is $currentBalance")
