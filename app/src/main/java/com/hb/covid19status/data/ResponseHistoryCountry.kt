package com.hb.covid19status.data

import com.google.gson.annotations.SerializedName
import com.hb.covid19status.model.HistoryStats
import java.io.Serializable


data class ResponseHistoryCountry(

    @SerializedName("country")
    val country: String,

    @SerializedName("stat_by_country")
    val stat_by_country: List<HistoryStats>
) : Serializable