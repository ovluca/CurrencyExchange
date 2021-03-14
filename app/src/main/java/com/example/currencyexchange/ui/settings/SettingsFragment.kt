package com.example.currencyexchange.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyexchange.MainActivity
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.SettingsFragmentBinding
import com.example.currencyexchange.settings.SettingsPreferences

class SettingsFragment : Fragment() {
    private lateinit var viewModel: SettingsViewModel
    private var _binding: SettingsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).binding.topAppBar.menu.setGroupVisible(R.id.group,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        setupCurrencies()
        setupInterval()

    }

    private fun setupCurrencies() {

        binding.currencyAutocomplete.setText(SettingsPreferences.getCurrency(requireContext()))

        val items = viewModel.getCurrencyList(requireContext());
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.currencyAutocomplete as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.currencyAutocomplete.setOnItemClickListener { _: AdapterView<*>, _: View, i: Int, _: Long ->
            run {
                viewModel.saveCurrency(requireActivity(), items[i])
            }
        }
    }


    private fun setupInterval() {
        binding.intervalAutocomplete.setText(
            SettingsPreferences.getInterval(requireContext()).toString()
        )

        val items = viewModel.getInterval();
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.intervalAutocomplete as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.intervalAutocomplete.setOnItemClickListener { _: AdapterView<*>, _: View, i: Int, _: Long ->
            run {
                viewModel.saveInterval(requireActivity(), items[i])
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}