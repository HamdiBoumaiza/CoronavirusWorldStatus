package com.hb.covid19status.repository

import com.hb.covid19status.data.RemoteDataNotFoundException
import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResponseListCountriesAffected
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.datasource.AppDataSourceImplTest
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.model.WorldStats

class AppRepositoryImplTest(private val appDataSource: AppDataSourceImplTest) :
    AppRepository {

    override suspend fun worldWithStatsApi(): ResultData<WorldStats> {
        TODO("Not yet implemented")
    }

    override suspend fun worldWithStatsDb(): ResultData<WorldStats> {
        TODO("Not yet implemented")
    }

    override suspend fun worldWithStats(): ResultData<WorldStats> {
        TODO("Not yet implemented")
    }

    override suspend fun listCountriesWithStatsApi(): ResultData<List<CountryStat>> {
        return when (val result = appDataSource.listCountriesWithStats()) {
            is ResultData.Success -> {
                val response = result.data.countries_stat
                ResultData.Success(response)
            }
            is ResultData.Error -> {
                ResultData.Error(RemoteDataNotFoundException())
            }
        }
    }

    override suspend fun listCountriesWithStatsDb(): ResultData<List<CountryStat>> {
        TODO("Not yet implemented")
    }

    override suspend fun listCountriesWithStats(): ResultData<List<CountryStat>> {
        return listCountriesWithStatsApi()
    }

    override suspend fun historyByDateByCountryStats(
        country: String,
        date: String
    ): ResultData<ResponseHistoryCountry> {
        TODO("Not yet implemented")
    }

    override suspend fun affectedCountries(): ResultData<ResponseListCountriesAffected> {
        TODO("Not yet implemented")
    }

}