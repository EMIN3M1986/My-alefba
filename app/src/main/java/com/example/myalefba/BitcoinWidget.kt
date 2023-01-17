package com.example.myalefba

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.example.myalefba.model.repository.CryptoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of App Widget functionality.
 */

@AndroidEntryPoint
class BitcoinWidget : AppWidgetProvider() {

    @Inject
    lateinit var cryptoRepository: CryptoRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("babiWidget", "onUpdate")
        // There may be multiple widgets active, so update all of them
        CoroutineScope(Main).launch {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, cryptoRepository)
            }
        }
    }

}

internal suspend fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    cryptoRepository: CryptoRepository
) {

    Log.d("babiWidget", "updateAppWidget")
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.bitcoin_widget)

    cryptoRepository
        .getAveragePrice(4)
        .collect { price ->
            Log.d("babiWidget", "updateAppWidget price= $price")
            views.setTextViewText(R.id.appwidget_text, price)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
}



