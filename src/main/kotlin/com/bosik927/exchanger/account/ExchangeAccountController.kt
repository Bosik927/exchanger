package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.dto.AccountResponseDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/accounts")
class ExchangeAccountController(
    private val service: ExchangeAccountService
) {

    @GetMapping("/{uuid}")
    suspend fun fetchOne(@PathVariable uuid: String): ResponseEntity<AccountResponseDto> {
        val account = service.fetchOne(uuid)
        return ResponseEntity.ok(account.toDto());
    }

    @PostMapping
    suspend fun create(@RequestBody @Valid request: CreateAccountRequestDto): ResponseEntity<AccountResponseDto> {
        val account = service.create(request.toEntity())
        return ResponseEntity.ok(account.toDto());
    }
}