package com.example.myalefba

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.myalefba.model.services.BitcoinService
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        loadData()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    private fun loadData() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val getPriceRequest =
            PeriodicWorkRequestBuilder<BitcoinService>(16, TimeUnit.MINUTES)
                .setConstraints(constraints)
                // Additional configuration
                .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "workerName",
                ExistingPeriodicWorkPolicy.REPLACE,
                getPriceRequest)
    }

}