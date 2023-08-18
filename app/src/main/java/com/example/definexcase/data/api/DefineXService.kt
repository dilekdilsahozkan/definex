package com.example.definexcase.data.api

import com.example.definexcase.base.CACHE_CONTROL_HEADER
import com.example.definexcase.base.CACHE_CONTROL_NO_CACHE
import com.example.definexcase.data.dto.LoginDto
import com.example.definexcase.data.dto.LoginRequestDto
import com.example.definexcase.data.dto.MarketDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DefineXService {
    @POST("/login")
    suspend fun login(@Body login: LoginRequestDto)
    : Response<LoginDto>

    @GET("/discoverFirstHorizontalList")
    @Headers("$CACHE_CONTROL_HEADER: $CACHE_CONTROL_NO_CACHE")
    suspend fun getHorizontal(@Header("token") token: String?)
    : Response<MarketDto>

    @GET("/discoverSecondHorizontalList")
    @Headers("$CACHE_CONTROL_HEADER: $CACHE_CONTROL_NO_CACHE")
    suspend fun getSecondHorizontal(@Header("token") token: String?)
    : Response<MarketDto>

    @GET("/discoverThirthTwoColumnList")
    @Headers("$CACHE_CONTROL_HEADER: $CACHE_CONTROL_NO_CACHE")
    suspend fun getColumnList(@Header("token") token: String?)
    : Response<MarketDto>
}