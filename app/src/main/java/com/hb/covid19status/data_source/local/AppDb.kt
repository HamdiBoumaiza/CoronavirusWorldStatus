package com.hb.covid19status.data_source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.model.RoomDataConverter
import com.hb.covid19status.model.WorldStats

@Database(entities = [CountryStat::class,WorldStats::class], version = 1)
@TypeConverters(RoomDataConverter::class)
abstract class AppDb : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDb::class.java, "app_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}