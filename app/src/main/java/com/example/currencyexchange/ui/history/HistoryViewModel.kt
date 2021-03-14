package com.example.currencyexchange.ui.history

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyexchange.api.helper.NetworkResponse
import com.example.currencyexchange.model.History
import com.example.currencyexchange.repository.ExchangeRepository
import com.example.currencyexchange.settings.Constants
import com.example.currencyexchange.settings.Constants.EUR
import com.example.currencyexchange.settings.Constants.RON
import com.example.currencyexchange.settings.Constants.USD
import com.example.currencyexchange.settings.OfflineData
import com.example.currencyexchange.settings.SettingsPreferences
import com.example.currencyexchange.settings.Utils
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ExchangeRepository()
    var dates: ArrayList<String> = ArrayList()

    var euro: ArrayList<Entry> = ArrayList()
    var usd: ArrayList<Entry> = ArrayList()
    var ron: ArrayList<Entry> = ArrayList()


    val usdChartLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val ronChartLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val eurChartLoaded: MutableLiveData<Boolean> = MutableLiveData()
    val noDataLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            getHistory()
        }
    }

    private suspend fun getHistory() {
        when (val response =
            repository.getHistory(
                getStartAt(),
                getEndAt(),
                getSymbols(),
                SettingsPreferences.getCurrency(getApplication())!!
            )) {
            is NetworkResponse.Success -> {
                processData(response.body)
                OfflineData.saveHistoryForOffline(getApplication(), response.body)

            }
            is NetworkResponse.ApiError -> {
                // TODO: 3/13/21
            }
            is NetworkResponse.NetworkError -> {
                val offLineData = OfflineData.getHistoryOfflineData(getApplication())
                if (offLineData != null) {
                    noDataLiveData.postValue(false)
                    processData(offLineData)
                } else {
                    noDataLiveData.postValue(true)
                }
            }
            is NetworkResponse.UnknownError -> {
                // TODO: 3/13/21
            }
        }
    }

    private fun processData(history: History) {
        formatDates(history.rates.toSortedMap().keys)

        var index = 0F
        history.rates.toSortedMap()
            .forEach { (_, value) ->
                run {

                    if (value.containsKey(EUR)) {
                        euro.add(Entry(index, value[EUR]!!))
                    }
                    if (value.containsKey(USD)) {
                        usd.add(Entry(index, value[USD]!!))
                    }
                    if (value.containsKey(RON)) {
                        ron.add(Entry(index, value[RON]!!))
                    }
                    index++
                }
            }

        if (euro.isNotEmpty()) {
            eurChartLoaded.postValue(true)
        }
        if (ron.isNotEmpty()) {
            ronChartLoaded.postValue(true)
        }
        if (usd.isNotEmpty()) {
            usdChartLoaded.postValue(true)
        }
    }

    private fun getEndAt(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    private fun getStartAt(): String {
        val tenDaysInMillis = 1036800000//12 days
        val date = Date(System.currentTimeMillis() - tenDaysInMillis)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    private fun getSymbols(): String {
        val symbols = arrayListOf(Constants.EUR, Constants.USD, RON)
        symbols.remove(SettingsPreferences.getCurrency(getApplication()))
        return symbols.toString().replace("[", "").replace("]", "").replace(", ", ",")
    }

    private fun formatDates(keys: MutableSet<String>) {
        val dates = ArrayList(keys)

        dates.forEach { value ->
            run {
                this.dates.add(Utils.changeDateFormat(value).toString())
            }
        }
    }

    fun getNoHistoryTextVisibility(): Int {
        if (noDataLiveData.value == true) {
            return View.VISIBLE
        }
        return View.GONE
    }
}