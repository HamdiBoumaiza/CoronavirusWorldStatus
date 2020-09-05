package com.hb.covid19status

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.datasource.AppDataSourceImplTest
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.repository.AppRepositoryImplTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(JUnit4::class)
class GetCountryStatsTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var appRepository: AppRepositoryImplTest

    @Before
    @Throws(IOException::class)
    fun init() {
        appRepository = AppRepositoryImplTest(AppDataSourceImplTest())
    }

    @Test
    fun getCountryStatsList() = runBlocking {
        var listCountryStats: List<CountryStat>? = null
        when (val response = appRepository.listCountriesWithStats()) {
            is ResultData.Success -> {
                listCountryStats = response.data
            }
        }
        Truth.assertThat(listCountryStats).isNotEmpty()
        Truth.assertThat(listCountryStats?.size).isAtLeast(5)
        Truth.assertThat(listCountryStats?.get(0)?.cases).matches("6,404,456")
        Truth.assertThat(listCountryStats?.get(1)?.total_recovered).isNotNull()
    }
}