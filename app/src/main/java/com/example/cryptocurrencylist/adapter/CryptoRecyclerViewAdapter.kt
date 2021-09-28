package com.example.cryptocurrencylist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrencylist.CryptoCurrencyData
import com.example.cryptocurrencylist.databinding.CryptoCurrencyCardViewBinding
import java.util.*
import kotlin.collections.ArrayList

class CryptoRecyclerViewAdapter(
    private val cryptos: ArrayList<CryptoCurrencyData>,
    private val listener: (CryptoCurrencyData) -> Unit
) :
    RecyclerView.Adapter<CryptoCurrencyViewHolder>() {
    private lateinit var binding: CryptoCurrencyCardViewBinding
    private lateinit var cryptoFiltered: ArrayList<CryptoCurrencyData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCurrencyViewHolder {
        binding = CryptoCurrencyCardViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoCurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptos.size
    }

    override fun onBindViewHolder(holder: CryptoCurrencyViewHolder, position: Int) {
        val item = cryptos[position]
        holder.itemView.setOnClickListener { listener(item) }

        return holder.bind(cryptos[position])
    }

    val initialCityDataList = ArrayList<CryptoCurrencyData>().apply {
        cryptos.let { addAll(it) }
    }

    fun getFilter(): Filter {
        return cryptoFilter
    }
    private val cryptoFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<CryptoCurrencyData> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                initialCityDataList.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().toLowerCase()
                initialCityDataList.forEach {
                    if (it.name?.toLowerCase(Locale.ROOT)?.contains(query)!!) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                cryptos.clear()
                cryptos.addAll(results.values as ArrayList<CryptoCurrencyData>)
                notifyDataSetChanged()
            }
        }
    }

}

class CryptoCurrencyViewHolder(private val binding: CryptoCurrencyCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(crypto: CryptoCurrencyData) {
        var nameForURL: String = crypto.name?.lowercase() ?: ""
        val symbolForURL: String = crypto.symbol?.lowercase() ?: ""
        nameForURL = nameForURL.replace(" ", "-")
        Glide.with(itemView.context)
            .load("https://cryptologos.cc/logos/$nameForURL-$symbolForURL-logo.png?v=013")
            .into(binding.cryptoCurrencyLogo)
        binding.cryptoCurrencyName.text = crypto.name
        binding.cryptoCurrencyRank.text = "#${crypto.rank.toString()}"
        binding.cryptoCurrencySymbol.text = crypto.symbol
    }


}

