package com.hb.covid19status.data

import com.google.gson.annotations.SerializedName
import com.hb.covid19status.model.CountryStat


data class ResponseListCountriesStats(

    @SerializedName("countries_stat")
    val countries_stat: List<CountryStat>
)