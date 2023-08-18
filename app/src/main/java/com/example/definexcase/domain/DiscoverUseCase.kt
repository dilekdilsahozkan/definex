package com.example.definexcase.domain

import com.example.definexcase.base.BaseResult
import com.example.definexcase.data.dto.MarketDto
import com.example.definexcase.data.repository.DefineXRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(private val repository: DefineXRepository){

    fun getHorizontal(token: String): Flow<BaseResult<MarketDto>> {
        return flow {
            val detail = repository.getHorizontal(token)
            if(detail.isSuccessful && detail.code() == 200){
                emit(
                    BaseResult.Success(detail.body() ?: MarketDto())
                )
            }
        }
    }

    fun getSecondHorizontal(token: String): Flow<BaseResult<MarketDto>> {
        return flow {
            val detail = repository.getSecondHorizontal(token)
            if(detail.isSuccessful && detail.code() == 200){
                emit(
                    BaseResult.Success(detail.body() ?: MarketDto())
                )
            }
        }
    }

    fun getColumnList(token: String): Flow<BaseResult<MarketDto>> {
        return flow {
            val detail = repository.getColumnList(token)
            if(detail.isSuccessful && detail.code() == 200){
                emit(
                    BaseResult.Success(detail.body() ?: MarketDto())
                )
            }
        }
    }
}