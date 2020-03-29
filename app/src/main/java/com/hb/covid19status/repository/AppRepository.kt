package com.hb.covid19status.repository

import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResponseListCountriesAffected
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.model.WorldStats

interface AppRepository {

    suspend fun worldWithStatsApi(): ResultData<WorldStats>
    suspend fun worldWithStatsDb(): ResultData<WorldStats>
    suspend fun worldWithStats(): ResultData<WorldStats>

    suspend fun listCountriesWithStatsApi(): ResultData<List<CountryStat>>
    suspend fun listCountriesWithStatsDb(): ResultData<List<CountryStat>>
    suspend fun listCountriesWithStats(): ResultData<List<CountryStat>>

    suspend fun historyByDateByCountryStats(
        country: String,
        date: String
    ): ResultData<ResponseHistoryCountry>

    suspend fun affectedCountries(): ResultData<ResponseListCountriesAffected>

}