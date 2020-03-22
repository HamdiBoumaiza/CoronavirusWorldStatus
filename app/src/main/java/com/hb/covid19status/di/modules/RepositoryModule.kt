package com.hb.covid19status.di.modules

import com.hb.covid19status.api.ApiService
import com.hb.covid19status.data_source.local.AppDao
import com.hb.covid19status.data_source.remote.RemoteDataSourceImpl
import com.hb.covid19status.di.IoDispatcher
import com.hb.covid19status.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
class RepositoryModule {


    @Provides
    @Singleton
    fun provideAppRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        api: ApiService,
        appDao: AppDao
    ): AppRepositoryImpl {
        val userDataSource = RemoteDataSourceImpl(api, ioDispatcher)
        return AppRepositoryImpl(userDataSource, appDao, ioDispatcher)
    }
}