package com.hb.covid19status.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.databinding.ActivityMainBinding
import com.hb.covid19status.ui.list_stats.ListCountriesStatsFragment
import com.hb.covid19status.ui.search_history.SearchHistoryFragment
import com.hb.covid19status.ui.world_stats.WorldStatsFragment
import com.hb.covid19status.utils.replaceFragment
import com.hb.covid19status.utils.viewModelProvider
import javax.inject.Inject


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): MainViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponents.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
        binding.bottomNav.selectedItemId = R.id.action_country
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_world -> {
                replaceFragment(WorldStatsFragment(), R.id.frame_layout_container)
            }
            R.id.action_country -> {
                replaceFragment(ListCountriesStatsFragment(), R.id.frame_layout_container)
            }
            R.id.action_history -> {
                replaceFragment(SearchHistoryFragment(), R.id.frame_layout_container)
            }
        }
        return true
    }

}