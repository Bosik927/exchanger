package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.exception.AccountNotFoundException
import com.bosik927.exchanger.account.exception.IllegalUuidException
import com.bosik927.exchanger.account.model.ExchangeAccount
import jakarta.validation.Valid
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
@Validated
class ExchangeAccountService {

    private val accounts = ConcurrentHashMap<UUID, ExchangeAccount>()

    suspend fun fetchOne(uuid: String): ExchangeAccount {
        val accountUuid = try {
            UUID.fromString(uuid)
        } catch (e: IllegalArgumentException) {
            throw IllegalUuidException()
        }
        return accounts[accountUuid] ?: throw AccountNotFoundException(accountUuid)
    }

    suspend fun create(@Valid account: ExchangeAccount): ExchangeAccount {
        accounts.put(account.uuid, account)
        return account
    }
}