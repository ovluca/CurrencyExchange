package com.example.currencyexchange.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.currencyexchange.api.helper.NetworkResponse
import com.example.currencyexchange.model.ExchangeRates
import com.example.currencyexchange.repository.ExchangeRepository
import com.example.currencyexchange.settings.OfflineData.getOfflineData
import com.example.currencyexchange.settings.OfflineData.saveDataForOffline
import com.example.currencyexchange.settings.SettingsPreferences
import com.example.currencyexchange.settings.Utils.getCurrentDate
import kotlinx.coroutines.*

class ExchangeRatesViewModel(application: Application) : AndroidViewModel(application) {
    var exchangeRatesData: MutableLiveData<ExchangeRates> = MutableLiveData()
    var networkErrorData: MutableLiveData<Boolean> = MutableLiveData()

    var date: String = getCurrentDate()

    private val repository = ExchangeRepository()
    private lateinit var job: Job

    fun startGetExchangeRatesJob() {
        job = startRepeatingJob((SettingsPreferences.getInterval(getApplication()) * 1000).toLong())
    }

    private fun startRepeatingJob(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                getRates()
                delay(timeInterval)
            }
        }
    }


    private suspend fun getRates() {
        when (val response =
            repository.getExchangeRates(SettingsPreferences.getCurrency(getApplication())!!)) {
            is NetworkResponse.Success -> {
                exchangeRatesData.postValue(response.body)
                networkErrorData.postValue(false)
                date = getCurrentDate()
                saveDataForOffline(getApplication(), response.body)
            }
            is NetworkResponse.NetworkError -> {
                networkErrorData.postValue(true)
                val offLineData = getOfflineData(getApplication())

                if (offLineData != null) {
                    date = offLineData.date
                    exchangeRatesData.postValue(offLineData)
                }
            }
            is NetworkResponse.ApiError -> {
                // TODO: 3/13/21
            }

            is NetworkResponse.UnknownError -> {
                // TODO: 3/13/21
            }
        }
    }

    fun cancelGetExchangeRatesJob() {
        job.cancel()
    }


    fun getNetworkErrorVisibility(): Int {
        return if (networkErrorData.value == true) {
            View.VISIBLE
        } else View.GONE
    }


}