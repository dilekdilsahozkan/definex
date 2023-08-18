package com.example.definexcase.data.dto

data class LoginDto(
    val token: String? = null
)

data class LoginRequestDto(
    val email: String? = null,
    val password: String? = null
)
