package com.bosik927.exchanger.currency.exception

class SameCurrencyConversionException : RuntimeException("SourceCurrency and targetCurrency cannot be the same")