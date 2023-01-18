package com.example.myalefba.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myalefba.model.db.AppDatabase
import com.example.myalefba.model.db.dao.BtcDao
import com.example.myalefba.util.AppConstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppConstance.DATABASE_NAME
        )
            .setQueryCallback(object : RoomDatabase.QueryCallback {
                override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                    Log.d(
                        "BitcoinRoom",
                        "SQL Query: $sqlQuery SQL Args: $bindArgs"
                    )
                }

            }, Executors.newSingleThreadExecutor())
            .build()
    }

    @Provides
    fun provideBtcFeeDao(appDatabase: AppDatabase): BtcDao {
        return appDatabase.BtcDao()
    }

}
