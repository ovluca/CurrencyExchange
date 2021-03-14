package com.example.currencyexchange.api

import com.example.currencyexchange.api.helper.NetworkResponse
import com.example.currencyexchange.model.Error
import com.example.currencyexchange.model.ExchangeRates
import com.example.currencyexchange.model.History
import com.example.currencyexchange.settings.Constants.BASE
import com.example.currencyexchange.settings.Constants.END_AT
import com.example.currencyexchange.settings.Constants.PATH_HISTORY
import com.example.currencyexchange.settings.Constants.PATH_LATEST
import com.example.currencyexchange.settings.Constants.START_AT
import com.example.currencyexchange.settings.Constants.SYMBOLS
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API Requests
 * */
interface ApiService {

    /**
     * API request for retrieving exchange rates
     * @param base base currency used for exchange rates
     * @return NetworkResponse<ExchangeRates,Error>
     * */
    @GET(PATH_LATEST)
    suspend fun getExchangeRates(@Query(BASE) base: String): NetworkResponse<ExchangeRates, Error>


    /**
     * API request for retrieving exchange rates history
     * @param startAt starting day
     * @param endAt ending day
     * @param symbols currencies requested
     * @param base base currency
     * */
    @GET(PATH_HISTORY)
    suspend fun getHistory(
        @Query(START_AT) startAt: String,
        @Query(END_AT) endAt: String,
        @Query(SYMBOLS) symbols: String,
        @Query(BASE) base: String
    ): NetworkResponse<History, Error>
}