package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.exception.AccountNotFoundException
import com.bosik927.exchanger.account.model.Account
import com.bosik927.exchanger.currency.CurrencyCode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*
import kotlinx.coroutines.test.runTest
import kotlin.test.assertFailsWith

class AccountServiceTest {

    private lateinit var accountService: AccountService

    @BeforeEach
    fun setUp() {
        accountService = AccountService()
    }

    @Test
    fun `should fetch an existing account successfully`() = runTest {
        val account = Account(
            uuid = UUID.randomUUID(),
            name = "John",
            surname = "Doe",
            balances = mapOf(CurrencyCode.PLN to BigDecimal("1000"))
        )
        accountService.create(account)

        val fetchedAccount = accountService.fetchOne(account.uuid)

        assertEquals(account, fetchedAccount)
    }

    @Test
    fun `should throw AccountNotFoundException when account does not exist`() = runTest {
        val nonExistentUuid = UUID.randomUUID()

        val exception = assertFailsWith<AccountNotFoundException> {
            accountService.fetchOne(nonExistentUuid)
        }
        assertEquals("Account not found for uuid [$nonExistentUuid]", exception.message)
    }

    @Test
    fun `should create a new account successfully`() = runTest {
        val account = Account(
            uuid = UUID.randomUUID(),
            name = "Jane",
            surname = "Doe",
            balances = mapOf(CurrencyCode.PLN to BigDecimal("2000"))
        )

        val createdAccount = accountService.create(account)

        assertEquals(account, createdAccount)
        assertEquals(createdAccount, accountService.fetchOne(account.uuid))
    }

    @Test
    fun `should update an existing account successfully`() = runTest {
        val account = Account(
            uuid = UUID.randomUUID(),
            name = "Alice",
            surname = "Doe",
            balances = mapOf(CurrencyCode.PLN to BigDecimal("1500"))
        )
        accountService.create(account)

        val updatedAccount = account.copy(balances = mapOf(CurrencyCode.PLN to BigDecimal("3000")))
        accountService.update(account.uuid, updatedAccount)

        val fetchedAccount = accountService.fetchOne(account.uuid)
        assertEquals(updatedAccount, fetchedAccount)
    }
}