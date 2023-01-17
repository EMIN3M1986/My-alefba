package com.example.myalefba.model.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "BTCFee",
    indices = [Index(value = ["get_date"], unique = true)])
data class BtcEntity (

    @PrimaryKey(autoGenerate = true)
    val uid: Int? = null,

    @ColumnInfo(name = "get_date")
    var getDate: String,
    @ColumnInfo(name = "fee")
    var fee: Double
)