package com.hb.covid19status.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.ui.details_stats.DetailsCountryStatsViewModel
import com.hb.covid19status.ui.list_stats.ListCountriesStatsViewModel
import com.hb.covid19status.ui.onboarding.OnboardingViewModel
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
    abstract fun bindListStatsVM(ListCountriesStatsViewModel: ListCountriesStatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsCountryStatsViewModel::class)
    abstract fun bindCountryStatsVM(detailsCountryStatsViewModel: DetailsCountryStatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorldStatsViewModel::class)
    abstract fun bindWorldStatsVM(worldStatsViewModel: WorldStatsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    abstract fun bindOnboardingVM(onboardingViewModel: OnboardingViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

}