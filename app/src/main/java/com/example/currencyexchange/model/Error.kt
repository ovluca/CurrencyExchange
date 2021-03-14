package com.example.currencyexchange.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
/**
 * API error
 * */
@JsonClass(generateAdapter = true)
class Error(
    @Json(name = "status")
    val status: String,

    @Json(name = "message")
    val message: String
)
