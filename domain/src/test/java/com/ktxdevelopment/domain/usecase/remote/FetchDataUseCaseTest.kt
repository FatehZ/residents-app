package com.ktxdevelopment.domain.usecase.remote

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.repo.FakeLocalRepository
import com.ktxdevelopment.domain.repo.FakeRemoteRepository
import com.ktxdevelopment.domain.usecase.local.SaveCitiesUseCase
import com.ktxdevelopment.domain.usecase.local.SaveCountriesUseCase
import com.ktxdevelopment.domain.usecase.local.SavePeopleUseCase
import com.ktxdevelopment.domain.util.TestData
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test


class FetchDataUseCaseTest {

    private lateinit var mockRepo: FakeRemoteRepository

    private lateinit var mockLocalRepo: FakeLocalRepository

    private lateinit var mockSaveCitiesUseCase: SaveCitiesUseCase

    private lateinit var mockSaveCountriesUseCase: SaveCountriesUseCase

    private lateinit var mockSavePeopleUseCase: SavePeopleUseCase

    private lateinit var fetchDataUseCase: FetchDataUseCase

    @Before
    fun setup() {
        mockRepo = FakeRemoteRepository()
        mockLocalRepo = FakeLocalRepository()
        mockSaveCitiesUseCase = SaveCitiesUseCase(mockLocalRepo)
        mockSaveCountriesUseCase = SaveCountriesUseCase(mockLocalRepo)
        mockSavePeopleUseCase = SavePeopleUseCase(mockLocalRepo)
        fetchDataUseCase = FetchDataUseCase(
            mockRepo,
            mockSaveCitiesUseCase,
            mockSaveCountriesUseCase,
            mockSavePeopleUseCase
        )


        runBlocking {
            mockRepo.saveData(TestData.responseData)
            mockLocalRepo.saveCities(TestData.cityList)
            mockLocalRepo.saveCities(TestData.cityList)
            mockLocalRepo.saveCountries(TestData.countryList)
            mockLocalRepo.savePeople(TestData.residentList)
        }
    }


    @Test
    fun `invoke fun should emit success resource and write data to DB`() = runBlocking {

        val result = fetchDataUseCase.invoke().toList()
        val cities = mockLocalRepo.getCities()
        val countries = mockLocalRepo.getCountries()
        val people = mockLocalRepo.getAllResidents()
        val peopleByCity = mockLocalRepo.getResidents(TestData.cityList.map { it.cityId })

        assertEquals(Resource.Loading, result.first())
        assertEquals(Resource.Success(0), result.last())
        assertEquals(TestData.cityList, cities.last())
        assertEquals(TestData.countryList, countries.last())
        assertEquals(TestData.residentList, people.last())
        assertEquals(TestData.residentList, peopleByCity.last())
    }
}