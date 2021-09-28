package com.example.cryptocurrencylist.api

import com.example.cryptocurrencylist.CryptoCurrencyData
import retrofit2.http.GET

interface CryptoAPI {
    @GET("v1/coins")
    suspend fun getCryptoAPI(): ArrayList<CryptoCurrencyData>
}