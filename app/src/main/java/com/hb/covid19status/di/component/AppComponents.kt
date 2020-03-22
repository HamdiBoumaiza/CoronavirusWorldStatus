package com.hb.covid19status.di.component

import android.content.Context
import com.hb.covid19status.data_source.local.AppDao
import com.hb.covid19status.data_source.local.AppDb
import com.hb.covid19status.di.modules.*
import com.hb.covid19status.ui.details_stats.DetailsCountriesStatsActivity
import com.hb.covid19status.ui.list_stats.ListCountriesStatsActivity
import com.hb.covid19status.ui.onboarding.OnboardingActivity
import com.hb.covid19status.ui.world_stats.WorldStatsActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        CoroutinesModule::class,
        StorageModule::class
    ]
)
interface AppComponents {
    fun context(): Context

    fun retrofit(): Retrofit

    fun appDao(): AppDao

    fun appDatabase(): AppDb

    fun inject(listCountriesStatsActivity: ListCountriesStatsActivity)
    fun inject(detailsCountriesStatsActivity: DetailsCountriesStatsActivity)
    fun inject(worldStatsActivity: WorldStatsActivity)
    fun inject(onBoardingActivity: OnboardingActivity)
}