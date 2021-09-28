package com.example.cryptocurrencylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import com.example.cryptocurrencylist.databinding.CryptoCurrencyInfoCardViewBinding


class CryptoInfoListViewAdapter(private val infoMap: MutableMap<String, String?>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return infoMap.count()
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        return 1
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val binding = CryptoCurrencyInfoCardViewBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )

        binding.text1.text = infoMap.keys.toTypedArray()[position]
        binding.text2.text = infoMap[infoMap.keys.toTypedArray()[position]]
        return binding.root
    }



}