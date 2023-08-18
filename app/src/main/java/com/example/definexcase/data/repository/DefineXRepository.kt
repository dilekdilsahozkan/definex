package com.example.definexcase.data.repository

import com.example.definexcase.data.dto.LoginDto
import com.example.definexcase.data.dto.LoginRequestDto
import com.example.definexcase.data.dto.MarketDto
import retrofit2.Response

interface DefineXRepository {
    suspend fun loginRequest(loginRequest: LoginRequestDto): Response<LoginDto>
    suspend fun getHorizontal(token: String?): Response<MarketDto>
    suspend fun getSecondHorizontal(token: String?): Response<MarketDto>
    suspend fun getColumnList(token: String?): Response<MarketDto>
}
