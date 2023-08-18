package com.example.definexcase.data.dto

data class MarketDto(
    val isSuccess: Boolean? = false,
    val message: String? = null,
    val list: List<DiscoverDto>? = null
)

data class DiscoverDto(
    val imageUrl : String? = null,
    val description : String? = null,
    val price : PriceDto? = null,
    val oldPrice : OldPriceDto? = null,
    val discount : String? = null,
    val ratePercentage : String? = null
)

data class PriceDto(
    val value : Double? = null,
    val currency : String? = null
)

data class OldPriceDto(
    val value : Double? = null,
    val currency : String? = null
)
