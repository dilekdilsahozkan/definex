package com.example.definexcase.base

import androidx.annotation.Keep

@Keep
data class SimpleDto<T>(val value: T) : BaseDto()

@Keep
open class BaseDto {
}