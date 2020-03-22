package com.hb.covid19status.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.model.WorldStats

@Dao
interface AppDao {

    @Query("SELECT * FROM CountryStat")
    suspend fun getListCountries(): List<CountryStat>

    @Insert(onConflict = REPLACE)
    suspend fun setListCountries(listCountriesStats: List<CountryStat?>)


    @Query("SELECT * FROM WorldStats")
    suspend fun getWorldStats(): WorldStats

    @Insert(onConflict = REPLACE)
    suspend fun setWorldStats(worldStats: WorldStats?)

}