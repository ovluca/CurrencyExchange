package com.example.currencyexchange.api

import com.example.currencyexchange.BuildConfig
import com.example.currencyexchange.api.helper.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
/**
* Return Retrofit instance
* */
val webservice: ApiService by lazy {

    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(ApiService::class.java)
}

/**
 * Return moshi instance
 *
 * Moshi is used for parsing JSON data
 * */
private val moshi: Moshi by lazy { Moshi.Builder().build() }


/**
 * Return OkHttpClient
 *
 * Interceptor set for logging API request
 * */

private val okHttpClient by lazy {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    OkHttpClient.Builder()
        .addInterceptor(interceptor = interceptor)
        .build()
}