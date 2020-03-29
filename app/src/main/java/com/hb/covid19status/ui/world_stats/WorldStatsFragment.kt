package com.hb.covid19status.ui.world_stats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.databinding.FragmentWorldStatsBinding
import com.hb.covid19status.model.WorldStats
import com.hb.covid19status.utils.hide
import com.hb.covid19status.utils.show
import com.hb.covid19status.utils.toast
import com.hb.covid19status.utils.viewModelProvider
import javax.inject.Inject


class WorldStatsFragment : Fragment() {

    private lateinit var stats: WorldStats
    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): WorldStatsViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: FragmentWorldStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        appComponents.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_world_stats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        getViewModel().getWorldStats()

    }


    private fun initObservers() {
        getViewModel().resultWorldStats.observe(this, Observer { worldStats ->
            worldStats?.let {
                showData(it)
                binding.fabShare.setOnClickListener { shareWorldStats() }
            } ?: kotlin.run {
                handleError()
            }
        })

        getViewModel().errorMessage.observe(this, Observer {
            handleError()
        })
        getViewModel().showLoading.observe(this, Observer { showLoading ->
            if (showLoading) binding.progress.show()
            else binding.progress.hide()
        })
    }

    private fun showData(it: WorldStats) {
        binding.groupVisibility.show()
        binding.world = it
        stats = it
    }

    private fun handleError() {
        activity?.toast(getString(R.string.error_message))
    }

    private fun shareWorldStats() {
        if (::stats.isInitialized) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            stats.apply {
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    String.format(
                        getString(R.string.share_stats_world_message),
                        total_cases,
                        total_deaths,
                        new_cases,
                        new_deaths,
                        total_recovered,
                        statistic_taken_at
                    )
                )
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    String.format(getString(R.string.share_stats), "the ic_world")
                )
            )
        }
    }

}