package com.example.cryptocurrencylist

data class CryptoCurrencyData(
    var id: String? = null,
    var name: String? = null,
    var symbol: String? = null,
    var rank: Int? = null,
    var is_new: Boolean? = null,
    var is_active: Boolean? = null,
    var type: String? = null
)

