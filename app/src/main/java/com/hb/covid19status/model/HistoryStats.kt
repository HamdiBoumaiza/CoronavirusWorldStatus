package com.hb.covid19status.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class HistoryStats(

    @SerializedName("id")
    val id: String,

    @SerializedName("country_name")
    val country_name: String,

    @SerializedName("total_cases")
    val total_cases: String,

    @SerializedName("total_deaths")
    val total_deaths: String,

    @SerializedName("total_recovered")
    val total_recovered: String,

    @SerializedName("new_cases")
    val new_cases: String,

    @SerializedName("new_deaths")
    val new_deaths: String,

    @SerializedName("active_cases")
    val active_cases: String,

    @SerializedName("serious_critical")
    val serious_critical: String,

    @SerializedName("total_cases_per1m")
    val total_cases_per1m: String,

    @SerializedName("record_date")
    val record_date: String

) : Serializable