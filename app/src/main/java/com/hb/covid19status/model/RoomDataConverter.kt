package com.hb.covid19status.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type


class RoomDataConverter : Serializable {

    @TypeConverter
    fun stringFromObject(list: CountryStat?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getObjectFromString(jsonString: String?): CountryStat? {
        val listType: Type = object : TypeToken<CountryStat?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


    @TypeConverter
    fun stringFromListObject(list: List<CountryStat?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getListObjectFromString(jsonString: String?): List<CountryStat?>? {
        val listType: Type = object : TypeToken<ArrayList<CountryStat?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}