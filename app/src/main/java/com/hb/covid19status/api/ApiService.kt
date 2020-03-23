package com.hb.covid19status.api

import com.hb.covid19status.data.ResponseListCountries
import com.hb.covid19status.model.WorldStats
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET(GENERAL_WORLD_STATS_URL)
    suspend fun getWorldStatsAsync(
        @Header("x-rapidapi-key") amount: String
    ): WorldStats

    @GET(ALL_COUNTRIES_STATS_URL)
    suspend fun getListCountriesStatsAsync(
        @Header("x-rapidapi-key") amount: String
    ): ResponseListCountries

}