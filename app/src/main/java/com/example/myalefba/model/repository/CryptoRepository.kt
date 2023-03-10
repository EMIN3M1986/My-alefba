package com.example.myalefba.model.repository

import com.example.myalefba.model.db.dao.BtcDao
import com.example.myalefba.model.db.entity.BtcEntity
import com.example.myalefba.model.network.CryptoAPICall
import com.example.myalefba.util.ExtensionFunction.convertPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CryptoRepository(
    private val cryptoAPICall: CryptoAPICall,
    private val btcDao: BtcDao
) {

    suspend fun toBtc(
        currency: String,
        value: Int
    ): Response<Double> {
        val response = cryptoAPICall.toBtc(currency, value)
        if (response.isSuccessful) {
            response.body()?.let {

//              val number = Random().nextInt(100000).toDouble()
                val date: String =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                btcDao.insertBtcFee(
                    BtcEntity(
                        getDate = date,
                        fee = 1 / it
                    )
                )
            }
        }
        return response
    }

    fun getAveragePrice(days: Int): Flow<String> =
        btcDao.getAveragePrice(days)
            .map { item ->
                item?.convertPrice(true, 2) ?: "0.0"
            }
            .flowOn(Dispatchers.IO)


}