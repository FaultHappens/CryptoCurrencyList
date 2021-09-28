package com.example.cryptocurrencylist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptocurrencylist.CryptoCurrencyInfoData
import com.example.cryptocurrencylist.adapter.CryptoInfoListViewAdapter
import com.example.cryptocurrencylist.api.CryptoInfoRetriever
import com.example.cryptocurrencylist.databinding.FragmentCryptoInfoBinding
import com.example.cryptocurrencylist.utils.isNetworkConnected
import kotlinx.coroutines.*

class CryptoInfoFragment : Fragment() {

    private val args: CryptoInfoFragmentArgs by navArgs()
    private lateinit var infoMap: MutableMap<String, String?>
    private lateinit var fragmentCryptoInfoBinding: FragmentCryptoInfoBinding
    private lateinit var fullUrl: String
    private lateinit var cryptoInfoListViewAdapter: CryptoInfoListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCryptoInfoBinding = FragmentCryptoInfoBinding.inflate(layoutInflater)
        return fragmentCryptoInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoMap = mutableMapOf()
        cryptoInfoListViewAdapter = CryptoInfoListViewAdapter(infoMap)
        fragmentCryptoInfoBinding.cryptoInfoListView.adapter = cryptoInfoListViewAdapter

        fullUrl = "/v1/coins/${args.cryptoId}"
        if (isNetworkConnected()) {
            retrieveCryptoInfo()
        } else {
            activity?.let {
                AlertDialog.Builder(it.applicationContext).setTitle("No Internet Connection")
                    .setMessage("Please check your internet connection and try again")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        }
    }

    private fun retrieveCryptoInfo() {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            activity?.let {
                AlertDialog.Builder(it.applicationContext).setTitle("Error")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        }
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            //4
            val resultList = CryptoInfoRetriever().getCryptoInfo(fullUrl)
            fillInfoMap(resultList)
            cryptoInfoListViewAdapter.notifyDataSetChanged()
            activity?.let {
                Glide.with(it.applicationContext)
                    .load(
                        "https://cryptologos.cc/logos/${
                            resultList.name.lowercase() .replace(
                                " ",
                                "-"
                            )
                        }-${resultList.symbol.lowercase() }-logo.png?v=013"
                    )
                    .into(fragmentCryptoInfoBinding.cryptoInfoImageView)
            }
            fragmentCryptoInfoBinding.cryptoInfoDescription.text =
                resultList.description
            fragmentCryptoInfoBinding.cryptoInfoName.text = resultList.name
            fragmentCryptoInfoBinding.cryptoInfoSymbol.text = resultList.symbol
        }
    }

    private fun fillInfoMap(infoData: CryptoCurrencyInfoData?): MutableMap<String, String?> {
        infoMap.clear()
        infoMap["First Data At"] = infoData?.first_data_at
        infoMap["Last Data At"] = infoData?.last_data_at
        infoMap["Hash Algorithm"] = infoData?.hash_algorithm
        infoMap["ID"] = infoData?.id
        infoMap["Links"] = infoData?.links.toString()
        infoMap["Organisation Structure"] = infoData?.org_structure
        infoMap["Proof Type"] = infoData?.proof_type
        infoMap["Rank"] = infoData?.rank.toString()
        infoMap["Started At"] = infoData?.started_at
        infoMap["Type"] = infoData?.type
        infoMap["White Paper"] = infoData?.whitepaper.toString()
        return infoMap
    }
}