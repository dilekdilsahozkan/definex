package com.example.definexcase.domain

import com.example.definexcase.base.BaseResult
import com.example.definexcase.data.dto.LoginDto
import com.example.definexcase.data.dto.LoginRequestDto
import com.example.definexcase.data.repository.DefineXRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: DefineXRepository) {

    fun login(loginRequest: LoginRequestDto): Flow<BaseResult<LoginDto>> {
        return flow {
            val detail = repository.loginRequest(loginRequest)
            if(detail.isSuccessful && detail.code() == 200){
                emit(
                    BaseResult.Success(detail.body() ?: LoginDto())
                )
            }
        }
    }
}