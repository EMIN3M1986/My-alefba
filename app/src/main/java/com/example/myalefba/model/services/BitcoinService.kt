package com.example.myalefba.model.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.myalefba.R
import com.example.myalefba.model.repository.CryptoRepository
import com.example.myalefba.ui.BitcoinWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay


@HiltWorker
class BitcoinService @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters,
    private val cryptoRepository: CryptoRepository
) : CoroutineWorker(context, workerParams) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        Log.d("MyService", "doWork---->")
        getToBtc()
        delay(30000)
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getToBtc() {
        cryptoRepository.toBtc("USD", 1)
        sendBroadcast()
        setForeground(createForegroundInfo())
    }

    private fun sendBroadcast() {
        Log.d("MyService", "toBtc--->")
        val man = AppWidgetManager.getInstance(context)
        val ids = man.getAppWidgetIds(
            ComponentName(context, BitcoinWidget::class.java)
        )
        val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(updateIntent)
    }


    // Creates an instance of ForegroundInfo which can be used to update the
    // ongoing notification.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createForegroundInfo(): ForegroundInfo {
        val id = context.getString(R.string.notification_channel_id)
        val title = context.getString(R.string.notification_title)
        val cancel = context.getString(R.string.stop_service)
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        // Create a Notification channel if necessary
        createChannel()

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText("Getting bitcoin price")
            .setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(1000, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            applicationContext.getString(R.string.notification_channel_id),
            name,
            importance
        ).apply {
            description = descriptionText
        }
        // Register the channel with the system

        notificationManager.createNotificationChannel(channel)
    }


}
