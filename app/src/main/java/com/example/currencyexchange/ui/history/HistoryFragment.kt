package com.example.currencyexchange.ui.history

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyexchange.MainActivity
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.HistoryFragmentBinding
import com.example.currencyexchange.settings.Constants
import com.example.currencyexchange.settings.Constants.EUR
import com.example.currencyexchange.settings.Constants.USD
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class HistoryFragment : Fragment() {
    private lateinit var viewModel: HistoryViewModel
    private var _binding: HistoryFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).binding.topAppBar.menu.setGroupVisible(
            R.id.group,
            false
        )
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)


        viewModel.usdChartLoaded.observe(viewLifecycleOwner, {
            binding.usdChart.visibility = View.VISIBLE
            renderUSDChart()
        })

        viewModel.eurChartLoaded.observe(viewLifecycleOwner, {
            binding.euroChart.visibility = View.VISIBLE
            renderEURChart()
        })

        viewModel.ronChartLoaded.observe(viewLifecycleOwner, {
            binding.ronChart.visibility = View.VISIBLE
            renderRONChart()
        })

        viewModel.noDataLiveData.observe(viewLifecycleOwner, {
            binding.noHistoryTextView.visibility = viewModel.getNoHistoryTextVisibility()

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun renderUSDChart() {

        val leftAxis: YAxis = binding.usdChart.axisLeft
        leftAxis.removeAllLimitLines()

        leftAxis.enableGridDashedLine(5f, 5f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
        binding.usdChart.axisRight.isEnabled = false
        setUSDChart()
    }

    private fun setUSDChart() {
        binding.usdChart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.dates)

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(getChartLineDataSet(USD, viewModel.usd))
        val data = LineData(dataSets)
        binding.usdChart.data = data
        binding.usdChart.invalidate()
    }


    private fun renderEURChart() {

        val leftAxis: YAxis = binding.euroChart.axisLeft
        leftAxis.removeAllLimitLines()

        leftAxis.enableGridDashedLine(5f, 5f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
        binding.euroChart.axisRight.isEnabled = false
        setEURData()
    }

    private fun setEURData() {
        binding.euroChart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.dates)
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(getChartLineDataSet(EUR, viewModel.euro))
        val data = LineData(dataSets)
        binding.euroChart.data = data
        binding.euroChart.invalidate()
    }


    private fun getChartLineDataSet(chartName: String, values: ArrayList<Entry>): LineDataSet {
        val set1 = LineDataSet(values, chartName)
        set1.setDrawIcons(false)
        set1.enableDashedLine(5f, 5f, 0f)
        set1.enableDashedHighlightLine(5f, 5f, 0f)
        set1.color = Color.DKGRAY
        set1.setCircleColor(Color.DKGRAY)
        set1.lineWidth = 1f
        set1.circleRadius = 5f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 9f
        set1.setDrawFilled(true)
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
        set1.formSize = 15f

        return set1
    }

    private fun renderRONChart() {

        val leftAxis: YAxis = binding.ronChart.axisLeft
        leftAxis.removeAllLimitLines()

        leftAxis.enableGridDashedLine(5f, 5f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(false)
        binding.ronChart.axisRight.isEnabled = false
        setRONData()
    }

    private fun setRONData() {
        binding.ronChart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.dates)
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(getChartLineDataSet(Constants.RON, viewModel.ron))
        val data = LineData(dataSets)
        binding.ronChart.data = data
        binding.ronChart.invalidate()
    }

}