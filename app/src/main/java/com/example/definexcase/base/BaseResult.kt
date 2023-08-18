package com.example.definexcase.base

import androidx.annotation.Keep

sealed class BaseResult<out T : Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T>()
    data class Error <T : Any>(val error: T) : BaseResult<T>()
}