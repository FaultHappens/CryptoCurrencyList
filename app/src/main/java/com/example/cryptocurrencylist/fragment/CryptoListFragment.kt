package com.example.cryptocurrencylist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencylist.adapter.CryptoRecyclerViewAdapter
import com.example.cryptocurrencylist.databinding.FragmentCryptoListBinding
import com.example.cryptocurrencylist.utils.isNetworkConnected
import com.example.cryptocurrencylist.viewModel.CryptoListViewModel
import android.R
import android.util.Log
import android.view.Menu
import android.widget.SearchView


class CryptoListFragment : Fragment() {
    private lateinit var fragmentCryptoListBinding: FragmentCryptoListBinding
    private lateinit var cryptoRecyclerViewAdapter: CryptoRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCryptoListBinding = FragmentCryptoListBinding.inflate(layoutInflater)
        return fragmentCryptoListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: CryptoListViewModel by viewModels()
        model.getCrypto().observe(this, { users ->
            cryptoRecyclerViewAdapter = CryptoRecyclerViewAdapter(users) { item ->
                Navigation.findNavController(view).navigate(CryptoListFragmentDirections.actionCryptoListFragment2ToCryptoInfoFragment(
                    item.id.toString()
                ))
            }
            fragmentCryptoListBinding.recyclerView.adapter =cryptoRecyclerViewAdapter

        })
        fragmentCryptoListBinding.recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext)
        fragmentCryptoListBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                cryptoRecyclerViewAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                cryptoRecyclerViewAdapter.getFilter().filter(newText);
                return true
            }

        })


        fragmentCryptoListBinding.swipeRefresh.setOnRefreshListener {
            if (isNetworkConnected()) {
                fragmentCryptoListBinding.swipeRefresh.isRefreshing = false
            } else {
                activity?.applicationContext?.let {
                    AlertDialog.Builder(it).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }
        }
    }

}