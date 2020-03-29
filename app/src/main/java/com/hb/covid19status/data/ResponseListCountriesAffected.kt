package com.hb.covid19status.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ResponseListCountriesAffected(

    @SerializedName("affected_countries")
    val affected_countries: List<String>
) : Serializable