package com.bosik927.exchanger.currency

import com.bosik927.exchanger.account.AccountService
import com.bosik927.exchanger.account.model.Account
import com.bosik927.exchanger.currency.client.ExchangeRateResponseDto
import com.bosik927.exchanger.currency.client.NbpExchangeRateClient
import com.bosik927.exchanger.currency.client.Rate
import com.bosik927.exchanger.currency.exception.BalanceNotFoundException
import com.bosik927.exchanger.currency.exception.InsufficientBalanceException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Assertions.assertEquals

class CurrencyConversionServiceTest {

    private lateinit var currencyConversionService: CurrencyConversionService
    private lateinit var accountService: AccountService
    private lateinit var nbpExchangeRateClient: NbpExchangeRateClient
    private val usdExchangeRate = BigDecimal("4.0")

    @BeforeEach
    fun setUp() {
        accountService = mock()
        nbpExchangeRateClient = mock()
        currencyConversionService = CurrencyConversionService(accountService, nbpExchangeRateClient)
        whenever(runBlocking { nbpExchangeRateClient.getExchangeRate(CurrencyCode.USD) }).thenReturn(mockExchangeRate())
    }

    private fun mockExchangeRate() = ExchangeRateResponseDto(
        table = "A",
        currency = "US Dollar",
        code = "USD",
        rates = listOf(Rate(no = "001/A/NBP/2023", effectiveDate = "2023-01-01", mid = usdExchangeRate))
    )

    private fun mockAccount(plnBalance: BigDecimal, usdBalance: BigDecimal? = null) = Account(
        uuid = UUID.randomUUID(),
        name = "John",
        surname = "Doe",
        balances = mapOf(CurrencyCode.PLN to plnBalance).plus(
            usdBalance?.let { mapOf(CurrencyCode.USD to it) } ?: emptyMap()
        )
    )

    @Test
    fun `should successfully convert PLN to USD`(): Unit = runTest {
        val account = mockAccount(plnBalance = BigDecimal("1000"), usdBalance = BigDecimal("50"))
        whenever(accountService.fetchOne(account.uuid)).thenReturn(account)

        currencyConversionService.convertCurrency(
            account.uuid, CurrencyCode.PLN, CurrencyCode.USD, BigDecimal("500")
        )

        verify(accountService).update(eq(account.uuid), check {
            assertEquals(BigDecimal("500"), it.balances[CurrencyCode.PLN])
            assertEquals(BigDecimal("175.000000"), it.balances[CurrencyCode.USD])
        })
    }

    @Test
    fun `should throw InsufficientBalanceException for insufficient PLN balance`(): Unit = runTest {
        val account = mockAccount(plnBalance = BigDecimal("100"), usdBalance = BigDecimal("50"))
        whenever(accountService.fetchOne(account.uuid)).thenReturn(account)

        assertFailsWith<InsufficientBalanceException> {
            currencyConversionService.convertCurrency(
                account.uuid, CurrencyCode.PLN, CurrencyCode.USD, BigDecimal("500")
            )
        }
    }

    @Test
    fun `should throw BalanceNotFoundException for missing USD balance`(): Unit = runTest {
        val account = mockAccount(plnBalance = BigDecimal("1000"))
        whenever(accountService.fetchOne(account.uuid)).thenReturn(account)

        assertFailsWith<BalanceNotFoundException> {
            currencyConversionService.convertCurrency(
                account.uuid, CurrencyCode.PLN, CurrencyCode.USD, BigDecimal("500")
            )
        }
    }

    @Test
    fun `should successfully convert USD to PLN`(): Unit = runTest {
        val account = mockAccount(plnBalance = BigDecimal("500"), usdBalance = BigDecimal("100"))
        whenever(accountService.fetchOne(account.uuid)).thenReturn(account)

        currencyConversionService.convertCurrency(
            account.uuid, CurrencyCode.USD, CurrencyCode.PLN, BigDecimal("50")
        )

        verify(accountService).update(eq(account.uuid), check {
            assertEquals(BigDecimal("50"), it.balances[CurrencyCode.USD])
            assertEquals(BigDecimal("700.0"), it.balances[CurrencyCode.PLN])
        })
    }
}
