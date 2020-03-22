package com.hb.covid19status.ui.details_stats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.databinding.ActivityDetailsStatsBinding
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.utils.COUNTRY_STATS_EXTRA
import com.hb.covid19status.utils.viewModelProvider
import javax.inject.Inject

class DetailsCountriesStatsActivity : AppCompatActivity() {

    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): DetailsCountryStatsViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: ActivityDetailsStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponents.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_stats)

        initViews()
    }

    private fun initViews() {
        intent?.let {
            val countryStat = it.getSerializableExtra(COUNTRY_STATS_EXTRA) as CountryStat
            binding.country = countryStat
        }
    }

}