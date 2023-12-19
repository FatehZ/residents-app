package com.ktxdevelopment.data.local.repo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.local.db.ResidentDatabase
import com.ktxdevelopment.data.util.TestData
import com.ktxdevelopment.data.util.toCityEntity
import com.ktxdevelopment.data.util.toCountryEntity
import com.ktxdevelopment.data.util.toResidentEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalRepositoryImplTest {

    private lateinit var database: ResidentDatabase
    private lateinit var countryDao: CountryDao
    private lateinit var personDao: PersonDao
    private lateinit var cityDao: CityDao
    private lateinit var localRepository: LocalRepositoryImpl

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ResidentDatabase::class.java).allowMainThreadQueries().build()

        countryDao = database.countryDao()
        personDao = database.personDao()
        cityDao = database.cityDao()
        localRepository = LocalRepositoryImpl(countryDao, personDao, cityDao)

        runTest {
            countryDao.insertCountries(TestData.exCountryList.toCountryEntity())
            cityDao.insertCities(TestData.exCityList.toCityEntity())
            personDao.insertPerson(TestData.exResidentList.toResidentEntity())
        }

    }

    @After
    fun cleanup() {
        database.close()
    }



    @Test
    fun getCountriesShouldReturnListOfCountriesFromTheDatabase() = runTest {

        val result = localRepository.getCountries().first()

        assertEquals(TestData.exCountryList, result)
    }

    @Test
    fun getCitiesShouldReturnListOfCitiesFromTheDatabase() = runTest {

        val result = localRepository.getCities().first()

        assertEquals(TestData.exCityList, result)
    }

    @Test
    fun getAllResidentsShouldReturnListOfResidentsFromTheDatabase() = runTest {

        val result = localRepository.getAllResidents().first()

        assertEquals(TestData.exResidentList, result)
    }
}