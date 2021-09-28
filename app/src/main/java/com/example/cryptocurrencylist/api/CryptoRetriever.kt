package com.example.cryptocurrencylist.api

import com.example.cryptocurrencylist.CryptoCurrencyData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoRetriever {
    private val service: CryptoAPI
    companion object {
        const val BASE_URL = "https://api.coinpaprika.com//"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(CryptoAPI::class.java)
    }



    suspend fun getCrypto(): ArrayList<CryptoCurrencyData> =
        service.getCryptoAPI()


}