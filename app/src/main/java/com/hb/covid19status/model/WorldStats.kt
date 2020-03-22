package com.hb.covid19status.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "WorldStats", primaryKeys = ["total_cases"])
data class WorldStats(

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

    @SerializedName("statistic_taken_at")
    var statistic_taken_at: String
) : Serializable