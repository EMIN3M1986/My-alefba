package com.example.myalefba.model.repository

import com.example.myalefba.model.repository.CryptoRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CryptoRepositoryTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cryptoRepository: CryptoRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toBtc() = runTest {
        val response = cryptoRepository.toBtc("USD", 1)
        assertTrue(response.isSuccessful)
        assertNull(response.errorBody())
        assertNotNull(response.body())

    }

}