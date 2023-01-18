package com.example.myalefba.ui

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.work.*
import com.example.myalefba.R
import com.example.myalefba.model.repository.CryptoRepository
import com.example.myalefba.model.services.BitcoinService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BitcoinWidget : AppWidgetProvider() {

    @Inject
    lateinit var cryptoRepository: CryptoRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("BitcoinWidget", "onUpdate")

        CoroutineScope(Main).launch {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, cryptoRepository)
            }
        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let { loadData(it) }
    }

}

internal suspend fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    cryptoRepository: CryptoRepository
) {

    val views = RemoteViews(context.packageName, R.layout.bitcoin_widget)

    cryptoRepository
        .getAveragePrice(4)
        .collect { price ->
            Log.d("BitcoinWidget", "updateAppWidget price= $price")
            views.setTextViewText(R.id.appwidget_text, price)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
}

internal fun loadData(context: Context) {

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val getPriceRequest =
        PeriodicWorkRequestBuilder<BitcoinService>(16, TimeUnit.MINUTES)
            .setConstraints(constraints)
            // Additional configuration
            .build()

    WorkManager
        .getInstance(context)
        .enqueueUniquePeriodicWork(
            "workerName",
            ExistingPeriodicWorkPolicy.REPLACE,
            getPriceRequest
        )
}



