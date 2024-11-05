package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.exception.AccountNotFoundException
import com.bosik927.exchanger.account.exception.IllegalUuidException
import com.bosik927.exchanger.account.model.Account
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class AccountService {

    private val accounts = ConcurrentHashMap<UUID, Account>()

    suspend fun fetchOne(uuid: String): Account {
        val accountUuid = wrapToUuid(uuid)
        return accounts[accountUuid] ?: throw AccountNotFoundException(accountUuid)
    }

    suspend fun create(account: Account): Account {
        accounts.put(account.uuid, account)
        return account
    }

    private fun wrapToUuid(uuid: String): UUID {
        return try {
            UUID.fromString(uuid)
        } catch (e: IllegalArgumentException) {
            throw IllegalUuidException()
        }
    }
}