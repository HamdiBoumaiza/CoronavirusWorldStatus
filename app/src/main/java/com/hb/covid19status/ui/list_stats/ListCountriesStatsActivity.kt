package com.hb.covid19status.ui.list_stats

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.databinding.ActivityListStatsBinding
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.ui.details_stats.DetailsCountriesStatsActivity
import com.hb.covid19status.utils.*
import javax.inject.Inject

class ListCountriesStatsActivity : AppCompatActivity(),
    CountriesItemClickListener {

    private lateinit var countriesAdapter : CountriesStatsAdapter
    private val appComponents by lazy { MainApplication.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): ListCountriesStatsViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: ActivityListStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponents.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_stats)

        initViews()
        initObservers()
    }

    private fun initViews() {
        getViewModel().getListOfStats()
    }

    private fun initObservers() {
        getViewModel().resultListStats.observe(this, Observer {
            initRecycler(it)
        })
        getViewModel().errorMessage.observe(this, Observer {
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
                layoutManager = LinearLayoutManager(this@ListCountriesStatsActivity)
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
        Intent(this, DetailsCountriesStatsActivity::class.java).apply {
            putExtra(COUNTRY_STATS_EXTRA, countryStat)
            startActivity(this)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                countriesAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                countriesAdapter.filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
