package com.hb.covid19status.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.ui.list_stats.ListCountriesStatsViewModel
import com.hb.covid19status.ui.world_stats.WorldStatsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Module used to define the connection between the framework's [ViewModelProvider.Factory] and
 * our own implementation: [DaggerViewModelFactory].
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListCountriesStatsViewModel::class)
    abstract fun bindMainActivtyVM(mainActivityViewModel: ListCountriesStatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorldStatsViewModel::class)
    abstract fun bindOnboardingVM(onboardingViewModel: WorldStatsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

}