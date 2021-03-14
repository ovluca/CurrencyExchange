package com.example.currencyexchange.settings

import android.content.Context
import com.example.currencyexchange.R
import com.example.currencyexchange.settings.Constants.EUR

/**
 * Contains all methods used for saving and retrieving application settings like current currency and request interval
 * */
object SettingsPreferences {

    private const val SETTINGS = "settings"

    /**
     * Save base currency into shared preferences
     * */
    fun saveCurrency(context: Context, currency: String) {
        val sharedPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(context.getString(R.string.key_currency), currency)
            apply()
        }
    }

    /**
     * Get base currency from shared preferences
     *
     * @defValue EUR
     * */
    fun getCurrency(context: Context): String? {
        val sharedPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        return sharedPref.getString(context.getString(R.string.key_currency), EUR)
    }

    /**
     * Save request interval in seconds
     * */
    fun saveInterval(context: Context, interval: Int) {
        val sharedPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt(context.getString(R.string.key_interval), interval)
            apply()
        }
    }

    /**
     * Get request interval in seconds
     *
     * @defValue 3 seconds
     * */

    fun getInterval(context: Context): Int {
        val sharedPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        return sharedPref.getInt(context.getString(R.string.key_interval), 3)
    }
}