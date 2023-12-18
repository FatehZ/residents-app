package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.repo.FakeLocalRepository
import com.ktxdevelopment.domain.util.TestData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetCitiesUseCaseTest {


    private lateinit var localRepo: FakeLocalRepository

    private lateinit var saveCitiesUseCase: SaveCitiesUseCase

    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Before
    fun setUp() {
        localRepo = FakeLocalRepository()
        saveCitiesUseCase = SaveCitiesUseCase(localRepo)
        getCitiesUseCase = GetCitiesUseCase(localRepo)
    }

    @Test
    fun `invoke must return updated cities as flow successfully`() = runBlocking {

        saveCitiesUseCase.invoke(TestData.cityList)
        saveCitiesUseCase.invoke(TestData.exCityList)

        val result: ArrayList<Resource<List<CityModel>>> = arrayListOf()
        getCitiesUseCase.invoke().collect { result.add(it) }


        assertEquals(Resource.Loading, result.first())
        assertEquals(Resource.Success(TestData.exCityList), result.last())
    }
}