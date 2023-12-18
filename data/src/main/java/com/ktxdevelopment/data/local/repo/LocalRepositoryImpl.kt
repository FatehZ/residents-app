package com.ktxdevelopment.data.local.repo

import com.ktxdevelopment.data.local.dao.CityDao
import com.ktxdevelopment.data.local.dao.CountryDao
import com.ktxdevelopment.data.local.dao.PersonDao
import com.ktxdevelopment.data.util.toCityDomain
import com.ktxdevelopment.data.util.toCityEntity
import com.ktxdevelopment.data.util.toCountryDomain
import com.ktxdevelopment.data.util.toCountryEntity
import com.ktxdevelopment.data.util.toResidentDomain
import com.ktxdevelopment.data.util.toResidentEntity
import com.ktxdevelopment.domain.model.CityModel
import com.ktxdevelopment.domain.model.CountryModel
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var countryDao: CountryDao,
    private var personDao: PersonDao,
    private var cityDao: CityDao
) : LocalRepository {

    override suspend fun savePeople(people: List<ResidentModel>) {
        personDao.insertPerson(people.toResidentEntity())
    }

    override suspend fun saveCities(cities: List<CityModel>) {
        cityDao.insertCities(cities.toCityEntity())
    }

    override suspend fun saveCountries(countries: List<CountryModel>) {
        countryDao.insertCountries(countries.toCountryEntity())
    }

    override suspend fun getCountries(): Flow<List<CountryModel>> = countryDao.getAllCountries().map { it.toCountryDomain() }


    override suspend fun getCities(): Flow<List<CityModel>> = cityDao.getAllCities().map { it.toCityDomain() }

    override suspend fun getAllResidents() = personDao.getAllPeople().map { it.toResidentDomain() }
    override suspend fun clearPeople() = personDao.clearAll()
    override suspend fun clearCountries() = countryDao.clearAll()
    override suspend fun clearCities() = cityDao.clearAll()

    override suspend fun getResidents(cities: List<Long>): Flow<List<ResidentModel>> = personDao.getPeopleByCityIds(cities).map { it.toResidentDomain() }
}