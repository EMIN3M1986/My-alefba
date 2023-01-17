package com.example.myalefba.model.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPICall {

    @GET("/tobtc?")
    suspend fun toBtc(
        @Query("currency") currency: String,
        @Query("value") value: Int
    ): Response<Double>
}