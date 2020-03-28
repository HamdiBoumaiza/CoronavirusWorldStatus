package com.hb.covid19status.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "CountryStat", primaryKeys = ["country_name"])
data class CountryStat(
    @SerializedName("country_name")
    val country_name: String,

    @SerializedName("cases")
    val cases: String,

    @SerializedName("deaths")
    val deaths: String,

    @SerializedName("total_recovered")
    val total_recovered: String,

    @SerializedName("new_deaths")
    val new_deaths: String,

    @SerializedName("new_cases")
    val new_cases: String,

    @SerializedName("serious_critical")
    val serious_critical: String,

    @SerializedName("active_cases")
    val active_cases: String,

    @SerializedName("total_cases_per_1m_population")
    val total_cases_per_1m_population: String
) : Serializable