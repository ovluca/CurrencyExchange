package com.example.currencyexchange.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
/**
 * Parsing JSON
 * */
@JsonClass(generateAdapter = true)
data class History(
    val rates: Map<String, Map<String, Float>>,
    @Json(name = "start_at")
    val startAt: String,
    val base: String,
    @Json(name = "end_at")
    val endAt: String
)