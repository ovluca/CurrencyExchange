package com.example.currencyexchange.settings

import android.content.Context
import com.example.currencyexchange.model.ExchangeRates
import com.example.currencyexchange.model.History
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
/**
 * Contains all methods used for storing and retrieving offline data
 * */
object OfflineData {

    /**
     * Save Exchange Rates in data.txt, file located on internal storage
     *
     * Used for offline mode
     *
     * @param context
     * @param exchangeRates
     * */
    fun saveDataForOffline(context: Context, exchangeRates: ExchangeRates) {
        val jsonAdapter: JsonAdapter<ExchangeRates> = Moshi.Builder().build().adapter(
            ExchangeRates::class.java
        )

        val json = jsonAdapter.toJson(exchangeRates)
        val writer = FileWriter(context.filesDir.path + "/data.txt")
        writer.write(json)
        writer.close()
    }

    /**
     * Return Exchange Rates from data.txt
     *
     * @param context
     * @return ExchangeRates or null if file don't exists
     * */
    fun getOfflineData(context: Context): ExchangeRates? {
        return try {
            val reader = FileReader(context.filesDir.path + "/data.txt")
            val text = reader.readText()
            reader.close()

            val jsonAdapter: JsonAdapter<ExchangeRates> =
                Moshi.Builder().build().adapter(ExchangeRates::class.java)

            val exchangeRates: ExchangeRates? = jsonAdapter.fromJson(text)

            exchangeRates!!
        } catch (e: FileNotFoundException) {
            null
        }
    }

    /**
     * Save History in history.txt, file located on internal storage
     *
     * Used for offline mode
     *
     * @param context
     * @param history
     * */
    fun saveHistoryForOffline(context: Context, history: History) {
        val jsonAdapter: JsonAdapter<History> = Moshi.Builder().build().adapter(
            History::class.java
        )

        val json = jsonAdapter.toJson(history)
        val writer = FileWriter(context.filesDir.path + "/history.txt")
        writer.write(json)
        writer.close()
    }

    /**
     * Return History from history.txt
     *
     * @param context
     * @return History or null if file don't exists
     * */
    fun getHistoryOfflineData(context: Context): History? {
        return try {
            val reader = FileReader(context.filesDir.path + "/history.txt")
            val text = reader.readText()
            reader.close()

            val jsonAdapter: JsonAdapter<History> =
                Moshi.Builder().build().adapter(History::class.java)

            val history: History? = jsonAdapter.fromJson(text)

            history!!
        } catch (e: FileNotFoundException) {
            null
        }
    }
}