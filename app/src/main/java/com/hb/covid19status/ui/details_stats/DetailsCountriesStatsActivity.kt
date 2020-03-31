package com.hb.covid19status.ui.details_stats

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
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

    private fun showHistoryWithDate(responseHistoryCountry: ResponseHistoryCountry) {
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
            shareImageUri()
        }
    }

    private fun showCountryStats(countryStat: CountryStat) {
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

    private fun saveImageToExternalStorage(bitmap: Bitmap): Uri? {
        return FileProvider.getUriForFile(
            this@DetailsCountriesStatsActivity,
            "$packageName.fileprovider",
            setFileFromBitmap(bitmap)
        )
    }

    private fun setFileFromBitmap(image: Bitmap): File {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share.png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.close()
        return file
    }

    private fun shareImageUri() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_STREAM,
            saveImageToExternalStorage(getViewModel().takeScreenShot(binding.constraintParent)!!)
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }
}