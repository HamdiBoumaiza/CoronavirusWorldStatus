package com.hb.covid19status.ui.world_stats

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.databinding.ActivityWorldStatsBinding
import com.hb.covid19status.ui.list_stats.ListCountriesStatsActivity
import com.hb.covid19status.utils.hide
import com.hb.covid19status.utils.show
import com.hb.covid19status.utils.toast
import com.hb.covid19status.utils.viewModelProvider
import javax.inject.Inject


class WorldStatsActivity : AppCompatActivity() {

    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): WorldStatsViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: ActivityWorldStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponents.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_world_stats)

        initViews()
        initObservers()
    }

    private fun initViews() {
        getViewModel().getWorldStats()
        binding.tvMoreDetails.setOnClickListener {
            startActivity(Intent(this, ListCountriesStatsActivity::class.java))
        }
    }


    private fun initObservers() {
        getViewModel().resultWorldStats.observe(this, Observer { worldStats ->
            worldStats?.let { binding.world = it }
        })

        getViewModel().errorMessage.observe(this, Observer {
            handleError()
        })
        getViewModel().showLoading.observe(this, Observer { showLoading ->
            if (showLoading) binding.progress.show()
            else binding.progress.hide()
        })
    }

    private fun handleError() {
        toast(getString(R.string.error_message))
    }
}
