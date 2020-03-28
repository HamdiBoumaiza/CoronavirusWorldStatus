package com.hb.covid19status.repository

import com.hb.covid19status.data.RemoteDataNotFoundException
import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.data_source.local.AppDao
import com.hb.covid19status.data_source.remote.RemoteDataSource
import com.hb.covid19status.di.IoDispatcher
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.model.WorldStats
import com.hb.covid19status.utils.InternetUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val appDao: AppDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    private val isInternetOn = InternetUtil.isInternetOn()

    override suspend fun listCountriesWithStatsApi(): ResultData<List<CountryStat>> {
        return when (val result = remoteDataSource.listCountriesWithStats()) {
            is ResultData.Success -> {
                val response = result.data.countries_stat
                withContext(ioDispatcher) { appDao.setListCountries(response) }
                ResultData.Success(response)
            }
            is ResultData.Error -> {
                ResultData.Error(RemoteDataNotFoundException())
            }
        }
    }

    override suspend fun listCountriesWithStatsDb(): ResultData<List<CountryStat>> =
        withContext(ioDispatcher) {
            ResultData.Success(appDao.getListCountries())
        }

    override suspend fun listCountriesWithStats(): ResultData<List<CountryStat>> {
        return if (isInternetOn) {
            listCountriesWithStatsApi()
        } else {
            listCountriesWithStatsDb()
        }
    }


    override suspend fun worldWithStatsApi(): ResultData<WorldStats> {
        return when (val result = remoteDataSource.worldWithStats()) {
            is ResultData.Success -> {
                val response = result.data
                withContext(ioDispatcher) { appDao.setWorldStats(response) }
                ResultData.Success(response)
            }
            is ResultData.Error -> {
                ResultData.Error(RemoteDataNotFoundException())
            }
        }
    }

    override suspend fun worldWithStatsDb(): ResultData<WorldStats> =
        withContext(ioDispatcher) {
            ResultData.Success(appDao.getWorldStats())
        }

    override suspend fun worldWithStats(): ResultData<WorldStats> {
        return if (isInternetOn) {
            worldWithStatsApi()
        } else {
            worldWithStatsDb()
        }
    }


    override suspend fun historyByDateByCountryStats(
        country: String,
        date: String
    ): ResultData<ResponseHistoryCountry> {
        return when (val result = remoteDataSource.historyByDateByCountryStats(country, date)) {
            is ResultData.Success -> {
                ResultData.Success(result.data)
            }
            is ResultData.Error -> {
                ResultData.Error(RemoteDataNotFoundException())
            }
        }
    }

}