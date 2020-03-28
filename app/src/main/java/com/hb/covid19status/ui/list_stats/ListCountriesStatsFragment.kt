package com.hb.covid19status.ui.list_stats

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.databinding.FragmentListStatsBinding
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.ui.details_stats.DetailsCountriesStatsActivity
import com.hb.covid19status.utils.*
import javax.inject.Inject

class ListCountriesStatsFragment : Fragment(),
    CountriesItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var countriesAdapter: CountriesStatsAdapter
    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): ListCountriesStatsViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: FragmentListStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        appComponents.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_stats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        getViewModel().getListOfStats()
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.edittextSearch.onChange {
            if (::countriesAdapter.isInitialized)
                countriesAdapter.filter.filter(it)
        }
    }

    private fun initObservers() {
        getViewModel().resultListStats.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = false
            initRecycler(it)
        })
        getViewModel().errorMessage.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = false
            handleEmptyList()
        })
        getViewModel().showLoading.observe(this, Observer { showLoading ->
            if (showLoading) binding.progress.show()
            else binding.progress.hide()
        })
    }

    private fun initRecycler(list: List<CountryStat>) {
        if (!list.isNullOrEmpty()) {
            countriesAdapter = CountriesStatsAdapter(list, this)
            binding.recyclerListStats.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = countriesAdapter
            }
        } else {
            handleEmptyList()
        }
    }

    private fun handleEmptyList() {
        with(binding) {
            recyclerListStats.hide()
            tvErrorMessage.show()
            tvErrorMessage.text = getString(R.string.no_result)
        }
    }


    override fun onCountryItemClicked(countryStat: CountryStat) {
        Intent(activity, DetailsCountriesStatsActivity::class.java).apply {
            putExtra(COUNTRY_STATS_EXTRA, countryStat)
            startActivity(this)
        }
    }

    override fun onRefresh() {
        getViewModel().getListOfStats()
    }
}
