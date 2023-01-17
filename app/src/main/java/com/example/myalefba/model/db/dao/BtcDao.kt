package com.example.myalefba.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myalefba.model.db.entity.BtcEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface BtcDao {

    @Query("SELECT AVG(fee) FROM BTCFee ORDER BY uid DESC LIMIT :days ")
    fun getAveragePrice(days: Int): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBtcFee(btcEntity: BtcEntity)

}