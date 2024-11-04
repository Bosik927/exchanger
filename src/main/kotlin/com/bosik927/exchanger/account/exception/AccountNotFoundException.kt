package com.bosik927.exchanger.account.exception

import java.util.UUID

class AccountNotFoundException(uuid: UUID) : RuntimeException("Account not found for uuid [$uuid]")
