package com.bosik927.exchanger.account

import com.bosik927.exchanger.account.dto.CreateAccountRequestDto
import com.bosik927.exchanger.account.dto.AccountResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val service: AccountService
) {

    @GetMapping("/{uuid}")
    suspend fun fetchOne(@PathVariable uuid: UUID): ResponseEntity<AccountResponseDto> {
        val account = service.fetchOne(uuid)
        return ResponseEntity.ok(account.toResponseDto());
    }

    @PostMapping
    suspend fun create(@RequestBody request: CreateAccountRequestDto): ResponseEntity<AccountResponseDto> {
        CreateAccountRequestValidator.validate(request)
        val account = service.create(request.toEntity())
        return ResponseEntity.ok(account.toResponseDto());
    }
}