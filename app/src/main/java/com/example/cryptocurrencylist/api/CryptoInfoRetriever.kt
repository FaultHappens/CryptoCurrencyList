package com.example.cryptocurrencylist.api

import com.example.cryptocurrencylist.CryptoCurrencyInfoData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoInfoRetriever {
    private val service: CryptoInfoAPI

    companion object {
        const val BASE_URL = "https://api.coinpaprika.com//"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(CryptoInfoAPI::class.java)
    }

    suspend fun getCryptoInfo(fullUrl: String): CryptoCurrencyInfoData =
        service.getCryptoInfoAPI(fullUrl)

}