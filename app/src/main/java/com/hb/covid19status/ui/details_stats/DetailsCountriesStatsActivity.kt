package com.hb.covid19status.ui.details_stats

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    private lateinit var countryStat: CountryStat
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
            countryStat = it.getSerializableExtra(COUNTRY_STATS_EXTRA) as CountryStat
            binding.country = countryStat
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_share) {
            shareCountryStats()
            true
        } else super.onOptionsItemSelected(item)
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

}