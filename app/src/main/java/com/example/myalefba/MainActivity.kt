package com.example.myalefba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        loadData()
    }

    private fun loadData() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val getPriceRequest =
            PeriodicWorkRequestBuilder<MyService>(16, TimeUnit.MINUTES)
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