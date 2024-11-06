package com.bosik927.exchanger.currency.exception

import com.bosik927.exchanger.currency.CurrencyCode

class ExchangeRateServiceException(currencyCode: CurrencyCode) :
    RuntimeException("Failed to retrieve exchange rate for [$currencyCode]")