package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.FakeLocalRepository
import com.ktxdevelopment.domain.util.TestData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetResidentsByCitiesUseCaseTest {

    private lateinit var getResidentsByCitiesUseCase: GetResidentsByCitiesUseCase
    private lateinit var localRepo: FakeLocalRepository
    private lateinit var saveResidentsUseCase: SavePeopleUseCase


    @Before
    fun setUp() {
        localRepo = FakeLocalRepository()
        saveResidentsUseCase = SavePeopleUseCase(localRepo)
        getResidentsByCitiesUseCase = GetResidentsByCitiesUseCase(localRepo)
    }

    @Test
    fun `invoke must return updated all residents as flow successfully`() = runBlocking {
        saveResidentsUseCase.invoke(TestData.residentList)
        saveResidentsUseCase.invoke(TestData.exResidentList)

        val result: ArrayList<Resource<List<ResidentModel>>> = arrayListOf()
        getResidentsByCitiesUseCase.invoke().collect { result.add(it) }


        assertEquals(Resource.Loading, result.first())
        assertEquals(Resource.Success(TestData.exResidentList), result.last())
    }


    @Test
    fun `invoke must return residents by cities as flow successfully`() = runBlocking {
        saveResidentsUseCase.invoke(TestData.residentList)
        saveResidentsUseCase.invoke(TestData.exResidentList)

        val result: ArrayList<Resource<List<ResidentModel>>> = arrayListOf()
        getResidentsByCitiesUseCase.invoke(listOf(TestData.exCityList[0])).collect { result.add(it) }

        assertEquals(Resource.Loading, result.first())
        assertEquals(Resource.Success(TestData.exResidentList.filter { it.cityId == TestData.exCityList[0].cityId }), result.last())
    }
}