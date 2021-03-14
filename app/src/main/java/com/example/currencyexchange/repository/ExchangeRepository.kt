package com.example.currencyexchange.repository

import com.example.currencyexchange.api.helper.NetworkResponse
import com.example.currencyexchange.api.webservice
import com.example.currencyexchange.model.Error
import com.example.currencyexchange.model.ExchangeRates
import com.example.currencyexchange.model.History


class ExchangeRepository {
    /**
     * return ExchangeRates
     *
     * @param baseCurrency
     * @return NetworkResponse
     * */
    suspend fun getExchangeRates(baseCurrency: String): NetworkResponse<ExchangeRates, Error> {
        return webservice.getExchangeRates(baseCurrency)
    }
    /**
     * return history
     *
     * @param startAt starting day
     * @param endAt ending day
     * @param symbols currencies requested
     * @param base base currency
     * */
    suspend fun getHistory(
        startAt: String,
        endAt: String,
        symbols: String,
        baseCurrency: String
    ): NetworkResponse<History, Error> {
        return webservice.getHistory(startAt, endAt, symbols, baseCurrency)
    }
}