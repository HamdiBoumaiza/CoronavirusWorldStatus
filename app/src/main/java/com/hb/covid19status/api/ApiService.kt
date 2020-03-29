package com.hb.covid19status.api

import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResponseListCountriesAffected
import com.hb.covid19status.data.ResponseListCountriesStats
import com.hb.covid19status.model.WorldStats
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {


    @GET(GENERAL_WORLD_STATS_URL)
    suspend fun getWorldStatsAsync(
        @Header(X_RAPIDAPI_KEY) amount: String
    ): WorldStats

    @GET(ALL_COUNTRIES_STATS_URL)
    suspend fun getListCountriesStatsAsync(
        @Header(X_RAPIDAPI_KEY) amount: String
    ): ResponseListCountriesStats

    @GET(HISTORY_BY_COUNTRIES_BY_DATE_URL)
    suspend fun getHistoryByCountryAndDate(
        @Header(X_RAPIDAPI_KEY) amount: String,
        @Query("country") country: String,
        @Query("date") date: String
    ): ResponseHistoryCountry

    @GET(LIST_AFFECTED_COUNTRY_URL)
    suspend fun getlistAffectedCountries(
        @Header(X_RAPIDAPI_KEY) amount: String
    ): ResponseListCountriesAffected

}