package com.webidias.cryptoapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.webidias.cryptoapp.R
import com.webidias.cryptoapp.apis.ApiInterface
import com.webidias.cryptoapp.apis.ApiUtilities
import com.webidias.cryptoapp.databinding.ActivityMainBinding
import com.webidias.cryptoapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencyList()

        return binding.root
    }

    private fun getTopCurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

                if (response.isSuccessful) {
                    val marketModel = response.body()

                    // Check if the response body is not null and has the expected structure
                    if (marketModel != null && marketModel.data != null) {
                        val cryptoCurrencyList = marketModel.data.cryptoCurrencyList

                        // Process the data on the main thread
                        withContext(Dispatchers.Main) {
                            Log.d("Elon", "getTopCurrencyList: $cryptoCurrencyList")
                            // Update your UI or perform further processing here
                        }
                    } else {
                        Log.e("Elon", "Unexpected response structure or null data.")
                    }
                } else {
                    Log.e("Elon", "Network request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Elon", "Error during network request: ${e.message}", e)
            }
        }
    }


}