package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.exception.AccountNotFoundException
import com.bosik927.exchanger.account.model.Account
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class AccountService {

    private val accounts = ConcurrentHashMap<UUID, Account>()

    suspend fun fetchOne(uuid: UUID): Account {
        return accounts[uuid] ?: throw AccountNotFoundException(uuid)
    }

    suspend fun create(account: Account): Account {
        accounts.put(account.uuid, account)
        return account
    }

    suspend fun update(uuid: UUID, account: Account) {
        accounts.put(account.uuid, account)
    }
}