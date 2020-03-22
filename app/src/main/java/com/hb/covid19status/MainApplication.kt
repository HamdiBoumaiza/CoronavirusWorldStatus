package com.hb.covid19status

import android.app.Application
import com.facebook.stetho.Stetho
import com.hb.covid19status.di.component.AppComponents
import com.hb.covid19status.di.component.DaggerAppComponents
import com.hb.covid19status.di.modules.AppModule
import com.hb.covid19status.di.modules.StorageModule
import com.hb.covid19status.utils.InternetUtil
import timber.log.Timber

open class MainApplication : Application() {

    companion object {
        lateinit var appComponents: AppComponents
    }

    override fun onCreate() {
        super.onCreate()
        appComponents = initDagger(this)
        initStetho()
        initTimber()
        InternetUtil.init(this)
    }

    private fun initDagger(app: MainApplication): AppComponents =
        DaggerAppComponents.builder()
            .appModule(AppModule(app))
            .storageModule(StorageModule(app))
            .build()

    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}