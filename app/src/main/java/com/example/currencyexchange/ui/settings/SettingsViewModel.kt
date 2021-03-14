package com.example.currencyexchange.ui.settings

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.currencyexchange.R
import com.example.currencyexchange.settings.SettingsPreferences

class SettingsViewModel : ViewModel() {

    fun getCurrencyList(context: Context): Array<out String> {
        return context.resources.getStringArray(R.array.currencies)
    }

    fun saveCurrency(context: Activity, currency: String) {
        SettingsPreferences.saveCurrency(context = context, currency)
    }

    fun saveInterval(context: Context, interval: Int) {
        SettingsPreferences.saveInterval(context = context, interval)
    }

    fun getInterval(): List<Int> {
        return listOf(3, 5, 10)
    }

}