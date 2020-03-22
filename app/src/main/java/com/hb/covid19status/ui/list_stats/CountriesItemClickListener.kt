package com.hb.covid19status.ui.list_stats

import com.hb.covid19status.model.CountryStat


interface CountriesItemClickListener {
    fun onCountryItemClicked(countryStat: CountryStat)
}