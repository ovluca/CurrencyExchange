package com.example.currencyexchange.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.databinding.RowRateBinding

/**
 * ExchangeRates View Holder
 * */
class RatesViewHolder(private val binding: RowRateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(exchangeRate: String?) {
        binding.rateText.text = exchangeRate
    }

}
