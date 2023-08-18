package com.example.definexcase.data.repository

import com.example.definexcase.data.api.DefineXService
import com.example.definexcase.data.dto.LoginDto
import com.example.definexcase.data.dto.LoginRequestDto
import com.example.definexcase.data.dto.MarketDto
import retrofit2.Response
import javax.inject.Inject

class DefineXRepositoryImpl @Inject constructor(private val service: DefineXService): DefineXRepository {
    override suspend fun loginRequest(loginRequest: LoginRequestDto): Response<LoginDto> = service.login(loginRequest)
    override suspend fun getHorizontal(token: String?): Response<MarketDto> = service.getHorizontal(token)
    override suspend fun getSecondHorizontal(token: String?): Response<MarketDto> = service.getSecondHorizontal(token)
    override suspend fun getColumnList(token: String?): Response<MarketDto> = service.getColumnList(token)
}