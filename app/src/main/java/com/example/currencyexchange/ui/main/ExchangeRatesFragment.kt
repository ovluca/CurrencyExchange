package com.example.currencyexchange.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchange.MainActivity
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.ExchangeRatesFragmentBinding
import com.example.currencyexchange.model.ExchangeRates

class ExchangeRatesFragment : Fragment() {

    private lateinit var viewModel: ExchangeRatesViewModel
    private var _binding: ExchangeRatesFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExchangeRatesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).binding.topAppBar.menu.setGroupVisible(R.id.group, true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExchangeRatesViewModel::class.java)
        viewModel.startGetExchangeRatesJob()

        viewModel.exchangeRatesData.observe(viewLifecycleOwner, { exchangeRates ->
            run {
                setViews(exchangeRates)
            }
        })

        viewModel.networkErrorData.observe(viewLifecycleOwner, {
            binding.noInternetConnectionText.visibility = viewModel.getNetworkErrorVisibility()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.cancelGetExchangeRatesJob()
    }

    private fun setViews(exchangeRates: ExchangeRates) {
        binding.ratesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                ExchangeRatesAdapter(exchangeRates.rates.map { "${it.key} = ${it.value}" } as ArrayList<String>)
        }
        binding.dateTextView.text = viewModel.date
        binding.baseCurrencyTextView.text = exchangeRates.base
    }

}