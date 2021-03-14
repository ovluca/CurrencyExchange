package com.example.currencyexchange.model

import com.squareup.moshi.JsonClass

/**
 * Parsing Json
 * */
@JsonClass(generateAdapter = true)
data class ExchangeRates(val rates: Map<String, Float>, val base: String, val date: String)
