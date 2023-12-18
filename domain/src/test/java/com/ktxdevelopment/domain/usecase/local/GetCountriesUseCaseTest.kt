package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.repo.FakeLocalRepository
import com.ktxdevelopment.domain.util.TestData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetCountriesUseCaseTest {

    private lateinit var localRepo: FakeLocalRepository
    private lateinit var saveCountriesUseCase: SaveCountriesUseCase
    private lateinit var getCountriesUseCase: GetCountriesUseCase
    @Before
    fun setUp() {
        localRepo = FakeLocalRepository()
        saveCountriesUseCase = SaveCountriesUseCase(localRepo)
        getCountriesUseCase = GetCountriesUseCase(localRepo)
    }

    @Test
    fun `invoke must return updated countries as flow successfully`() = runBlocking {

        saveCountriesUseCase.invoke(TestData.countryList)
        saveCountriesUseCase.invoke(TestData.exCountryList)

        val result: ArrayList<Resource<List<CountryModel>>> = arrayListOf()
        getCountriesUseCase.invoke().collect { result.add(it) }


        assertEquals(Resource.Loading, result.first())
        assertEquals(Resource.Success(TestData.exCountryList), result.last())
    }
}