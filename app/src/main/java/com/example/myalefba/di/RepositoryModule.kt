package com.example.myalefba.di

import com.example.myalefba.model.db.dao.BtcDao
import com.example.myalefba.model.network.CryptoAPICall
import com.example.myalefba.model.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(
        cryptoAPICall: CryptoAPICall,
        btcDao: BtcDao
    ): CryptoRepository {
        return CryptoRepository(cryptoAPICall, btcDao)
    }

}