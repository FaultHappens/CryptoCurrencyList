package com.example.cryptocurrencylist.api

import com.example.cryptocurrencylist.CryptoCurrencyInfoData
import retrofit2.http.GET
import retrofit2.http.Url

interface CryptoInfoAPI {
    @GET
    suspend fun getCryptoInfoAPI(@Url url: String): CryptoCurrencyInfoData
}