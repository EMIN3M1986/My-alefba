package com.example.myalefba.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myalefba.model.db.dao.BtcDao
import com.example.myalefba.model.db.entity.BtcEntity

/**
 * The Room database for this app
 */
@Database(
    entities = [BtcEntity::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun BtcDao(): BtcDao

}