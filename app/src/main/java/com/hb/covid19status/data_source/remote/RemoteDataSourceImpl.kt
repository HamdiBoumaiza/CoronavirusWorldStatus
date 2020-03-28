/*
 * *
 *  * Created by Ahlem Jarrar on 8/23/19 9:05 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 8/23/19 9:05 AM
 *
 */

package com.hb.covid19status.data_source.remote

import com.hb.covid19status.BuildConfig
import com.hb.covid19status.api.ApiService
import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResponseListCountries
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.di.IoDispatcher
import com.hb.covid19status.model.WorldStats
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteDataSourceImpl(
    private val api: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    override suspend fun worldWithStats(): ResultData<WorldStats> =
        withContext(ioDispatcher) {
            val request =
                api.getWorldStatsAsync(BuildConfig.API_KEY)
            ResultData.Success(request)
        }


    override suspend fun listCountriesWithStats(): ResultData<ResponseListCountries> =
        withContext(ioDispatcher) {
            val request =
                api.getListCountriesStatsAsync(BuildConfig.API_KEY)
            ResultData.Success(request)
        }

    override suspend fun historyByDateByCountryStats(
        country: String,
        date: String
    ): ResultData<ResponseHistoryCountry> =
        withContext(ioDispatcher) {
            val request =
                api.getHistoryByCountryAndDate(BuildConfig.API_KEY, country, date)
            ResultData.Success(request)
        }

}