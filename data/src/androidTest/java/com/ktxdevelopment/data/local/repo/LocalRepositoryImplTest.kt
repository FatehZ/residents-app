package com.ktxdevelopment.data.local.repo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.local.db.ResidentDatabase
import com.ktxdevelopment.data.local.model.CityEntity
import com.ktxdevelopment.data.local.model.CountryEntity
import com.ktxdevelopment.data.local.model.ResidentEntity
import com.ktxdevelopment.data.util.toDomain
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*

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
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ResidentDatabase::class.java
        ).allowMainThreadQueries().build()

        countryDao = database.countryDao()
        personDao = database.personDao()
        cityDao = database.cityDao()
        localRepository = LocalRepositoryImpl(countryDao, personDao, cityDao)

    }

    @After
    fun cleanup() {
        database.close()
    }



    @Test
    fun getCountriesShouldReturnListOfCountriesFromTheDatabase() = runBlocking {
        // Arrange
        val countries = listOf(
            CountryEntity(1, "Country 1"),
            CountryEntity(2, "Country 2"),
            CountryEntity(3, "Country 3")
        )
        countryDao.insertCountries(countries)

        val expectedCountries = countries.map { it.toDomain() }

        // Act
        val result = localRepository.getCountries().last()

        // Assert
        assertEquals(expectedCountries, result)
    }

    @Test
    fun getCitiesShouldReturnListOfCitiesFromTheDatabase() = runBlocking {
        // Arrange
        val cities = listOf(
            CityEntity(1, 1L,"City 1"),
            CityEntity(2, 2L,"City 2"),
            CityEntity(3, 3L,"City 3")
        )
        cityDao.insertCities(cities)

        val expectedCities = cities.map { it.toDomain() }

        // Act
        val result = localRepository.getCities().last()

        // Assert
        assertEquals(expectedCities, result)
    }

    @Test
    fun getAllResidentsShouldReturnListOfResidentsFromTheDatabase() = runBlocking {
        // Arrange
        val residents = listOf(
            ResidentEntity(1, name = "Resident 1", cityId = 1L, surname = "Surname 1"),
            ResidentEntity(2, name = "Resident 2", cityId = 2L, surname = "Surname 2"),
            ResidentEntity(3, name = "Resident 3", cityId = 3L, surname = "Surname 3")
        )
        personDao.insertPerson(residents)

        val expectedResidents = residents.map { it.toDomain() }

        // Act
        val result = localRepository.getAllResidents().last()

        // Assert
        assertEquals(expectedResidents, result)
    }
}