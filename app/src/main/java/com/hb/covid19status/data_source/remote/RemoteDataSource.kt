package com.hb.covid19status.data_source.remote

import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResponseListCountries
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.model.WorldStats

interface RemoteDataSource {

    suspend fun worldWithStats(): ResultData<WorldStats>
    suspend fun listCountriesWithStats(): ResultData<ResponseListCountries>
    suspend fun historyByDateByCountryStats(country : String, date : String): ResultData<ResponseHistoryCountry>

}