package com.example.cryptocurrencylist.viewModel

import android.R
import android.app.Application
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.example.cryptocurrencylist.CryptoCurrencyData
import com.example.cryptocurrencylist.api.CryptoRetriever
import kotlinx.coroutines.*

class CryptoListViewModel(): ViewModel() {
    lateinit var resultList: ArrayList<CryptoCurrencyData>

    private val crypto: MutableLiveData<ArrayList<CryptoCurrencyData>> by lazy {
        MutableLiveData<ArrayList<CryptoCurrencyData>>().also {
            retrieveCrypto()
        }
    }

    fun getCrypto(): LiveData<ArrayList<CryptoCurrencyData>> {
        return crypto
    }

    fun retrieveCrypto(){
        viewModelScope.launch {
            resultList = CryptoRetriever().getCrypto()
            crypto.value = resultList
        }
    }

}
