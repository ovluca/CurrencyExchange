package com.example.currencyexchange.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.databinding.RowRateBinding
/**
 * Adapter for rendering Exchange Rates
 * */
class ExchangeRatesAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<RatesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatesViewHolder {
        val binding = RowRateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val item: String = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}