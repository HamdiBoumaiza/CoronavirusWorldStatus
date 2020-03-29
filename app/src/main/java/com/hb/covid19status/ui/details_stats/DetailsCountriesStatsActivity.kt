package com.hb.covid19status.ui.details_stats

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.databinding.ActivityDetailsStatsBinding
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.model.HistoryStats
import com.hb.covid19status.utils.COUNTRY_STATS_EXTRA
import com.hb.covid19status.utils.HISTORY_EXTRA
import com.hb.covid19status.utils.viewModelProvider
import javax.inject.Inject

class DetailsCountriesStatsActivity : AppCompatActivity() {

    private lateinit var countryStat: CountryStat
    private lateinit var responseHistoryCountry: ResponseHistoryCountry
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
            it.getSerializableExtra(HISTORY_EXTRA)?.let { response ->
                responseHistoryCountry = response as ResponseHistoryCountry
                showHistoryWithDate(responseHistoryCountry)
            }
            it.getSerializableExtra(COUNTRY_STATS_EXTRA)?.let { country ->
                countryStat = country as CountryStat
                showCountryStats(countryStat)
            }
            setClickListener()
        }
    }

    private fun showHistoryWithDate(responseHistoryCountry :ResponseHistoryCountry) {
        val historyStats: HistoryStats? = responseHistoryCountry.stat_by_country.last()
        with(binding) {
            historyStats?.apply {
                tvCountryName.text =
                    "${responseHistoryCountry.country} - ${historyStats.record_date.substring(
                        0, 10
                    )}"
                tvTotalCases.text = String.format(getString(R.string.total_cases), total_cases)
                tvTotalDeaths.text =
                    String.format(getString(R.string.total_deaths), total_deaths)
                tvNewCases.text = String.format(getString(R.string.new_cases), new_cases)
                tvSeriousCases.text =
                    String.format(getString(R.string.serious_cases), serious_critical)
                tvNewDeaths.text = String.format(getString(R.string.new_deaths), new_deaths)
                tvActiveCases.text =
                    String.format(getString(R.string.active_cases), active_cases)
                tvTotalRecovered.text =
                    String.format(getString(R.string.total_recovered), total_recovered)
                tvCasesPer1m.text =
                    String.format(getString(R.string.cases_per_1m), total_cases_per1m)
            }
        }

    }


    private fun setClickListener() {
        binding.fabShare.setOnClickListener {
            if (::responseHistoryCountry.isInitialized) {
                shareCountryHistoryStats()
            } else if (::countryStat.isInitialized) {
                shareCountryStats()
            }
        }
    }


    private fun showCountryStats(countryStat : CountryStat) {
        with(binding) {
            countryStat.apply {
                tvCountryName.text = country_name
                tvTotalCases.text = String.format(getString(R.string.total_cases), cases)
                tvTotalDeaths.text = String.format(getString(R.string.total_deaths), deaths)
                tvNewCases.text = String.format(getString(R.string.new_cases), new_cases)
                tvSeriousCases.text =
                    String.format(getString(R.string.serious_cases), serious_critical)
                tvNewDeaths.text = String.format(getString(R.string.new_deaths), new_deaths)
                tvActiveCases.text = String.format(getString(R.string.active_cases), active_cases)
                tvTotalRecovered.text =
                    String.format(getString(R.string.total_recovered), total_recovered)
                tvCasesPer1m.text =
                    String.format(getString(R.string.cases_per_1m), total_cases_per_1m_population)
            }
        }
    }

    private fun shareCountryStats() {
        if (::countryStat.isInitialized) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            countryStat.apply {
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    String.format(
                        getString(R.string.share_stats_message),
                        country_name,
                        cases,
                        deaths,
                        new_cases,
                        serious_critical,
                        new_deaths,
                        active_cases,
                        total_recovered,
                        total_cases_per_1m_population
                    )
                )
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    String.format(getString(R.string.share_stats), countryStat.country_name)
                )
            )
        }
    }

    private fun shareCountryHistoryStats() {
        val historyStats: HistoryStats? = responseHistoryCountry.stat_by_country.last()
        if (::responseHistoryCountry.isInitialized) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            historyStats?.apply {
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    String.format(
                        getString(R.string.share_history_message),
                        country_name,
                        total_cases,
                        total_deaths,
                        new_cases,
                        serious_critical,
                        new_deaths,
                        active_cases,
                        total_recovered,
                        total_cases_per1m,
                        record_date.substring(0, 10)
                    )
                )
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    String.format(getString(R.string.share_stats), countryStat.country_name)
                )
            )
        }
    }

}